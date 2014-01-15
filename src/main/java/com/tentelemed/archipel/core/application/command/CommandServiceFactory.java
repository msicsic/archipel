package com.tentelemed.archipel.core.application.command;

import com.google.common.eventbus.EventBus;
import com.tentelemed.archipel.core.application.ApplicationEvent;
import com.tentelemed.archipel.core.application.EventRegistry;
import com.tentelemed.archipel.core.application.EventStore;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.EntityId;
import com.tentelemed.archipel.core.domain.pub.AbstractDomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:31
 */
@Component
public class CommandServiceFactory {
    protected final static Logger log = LoggerFactory.getLogger(CommandServiceFactory.class);

    @Autowired EventStore eventStore;
    @Autowired EventBus eventBus;
    @Autowired EventRegistry registry;
    @Autowired ApplicationContext context;

    PlatformTransactionManager transactionManager;
    TransactionTemplate transactionTemplate;

    private TransactionTemplate getTxTemplate() {
        if (transactionManager == null) {
            try {
                transactionManager = (PlatformTransactionManager) context.getBean(PlatformTransactionManager.class);
            } catch (Exception e) {
                return null;
            }
        }
        if (transactionTemplate == null) {
            transactionTemplate = new TransactionTemplate(transactionManager);
        }
        return transactionTemplate;
    }

    public <M> M create(Class<M> iFace) {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("execute")) {
                    final Command cmd = (Command) args[0];
                    TransactionCallback callback = new TransactionCallback<Object>() {
                        @Override
                        public Object doInTransaction(TransactionStatus status) {
                            return genericExec(cmd);
                        }
                    };
                    if (getTxTemplate() != null) {
                        return getTxTemplate().execute(callback);
                    } else {
                        return callback.doInTransaction(null);
                    }
                } else {
                    return method.invoke(this, args);
                }
            }
        };
        Object res = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{iFace}, handler);
        return (M) res;
    }

    protected CmdRes genericExec(Command cmd) {
        // validation de la commande
        validate(cmd);

        // si cmd.id == null on assume que c'est une cmd de creation, il faut donc trouver le type
        // de l'agregat correspondant
        BaseAggregateRoot aggregate;
        if (cmd.id == null) {
            Class<? extends BaseAggregateRoot> aggregateClass = registry.getAggregateClassForCommand(cmd);
            if (aggregateClass == null) {
                throw new RuntimeException("No Aggregate Class defined in registry for Create COMMAND : " + cmd.getClass().getSimpleName());
            }
            aggregate = get(aggregateClass);
        } else {
            aggregate = get(cmd.id);
        }
        try {
            // parcourir les champs de type EntityId et les stocker dans la commande
            Class cmdClass = getRealClass(cmd.getClass());
            Class c = cmdClass;
            while (c != null) {
                for (Field field : c.getDeclaredFields()) {
                    if (field.getName().equals("id")) continue;
                    if (EntityId.class.isAssignableFrom(field.getType())) {
                        EntityId id = (EntityId) field.get(cmd);
                        BaseAggregateRoot value = get(id);
                        cmd.addData(id, value);
                    }
                }
                c = c.getSuperclass();
            }
            Method m = aggregate.getClass().getMethod("execute", cmdClass);
            CmdRes result = (CmdRes) m.invoke(aggregate, cmd);

            if (result == null) {
                throw new RuntimeException("Command result cannot be null : " + getClass().getSimpleName() + ".execute(" + cmdClass.getSimpleName() + ")");
            }

            if (result.events == null || result.events.size() == 0) {
                throw new RuntimeException(getClass().getSimpleName() + ".execute(" + cmdClass.getSimpleName() + ") must return at least one event");
            }

            // si la commande n'a pas d'id (mode creation), le premier evt doit etre un create
            if (cmd.id == null && !result.events.get(0).isCreate()) {
                throw new RuntimeException("Command " + cmdClass.getSimpleName() + " has null id and must return a CREATE event : " + result.events.get(0).getClass().getSimpleName());
            }

            // l'agregat doit rester valide
            aggregate.validate();

            post(result);
            return result;

        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof RuntimeException) {
                throw (RuntimeException) e.getTargetException();
            } else {
                throw new RuntimeException(e.getTargetException());
            }
        } catch (Exception e) {
            log.error(null, e);
            throw new RuntimeException(e);
        }
    }

    private Class getRealClass(Class c) {
        while (c != null && c.getName().contains("$$")) {
            c = c.getSuperclass();
        }
        return c;
    }

    protected <M extends EntityId> M post(CmdRes res) {
        eventStore.handleEvents(res);
        return (M) res.entityId;
    }

    protected <I extends EntityId> void post(BaseAggregateRoot<I> target, AbstractDomainEvent... events) {
        post(new CmdRes(target.getEntityId(), Arrays.asList(events)));
    }

    protected void post(ApplicationEvent... events) {
        for (ApplicationEvent event : events) {
            eventBus.post(event);
        }
    }

    protected <M extends EntityId> BaseAggregateRoot<M> get(M id) {
        return eventStore.get(id);
    }

    protected <M extends BaseAggregateRoot> M get(Class<M> clazz) {
        return eventStore.get(clazz);
    }

    private Validator validator;

    protected Validator getValidator() {
        if (validator == null) {
            this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        }
        return this.validator;
    }

    protected <M extends Command> M validate(M command) {
        Set violations = getValidator().validate(command);
        if (!violations.isEmpty()) {
            for (Object oviolation : violations) {
                ConstraintViolation violation = (ConstraintViolation) oviolation;
                log.warn("constraint violation : " + command.getClass().getSimpleName() + "." + violation.getPropertyPath() + " " + violation.getMessage());
            }
            throw new ConstraintViolationException(violations);
        }
        return command;
    }
}
