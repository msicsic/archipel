package com.tentelemed.archipel.core.application.service;

import com.google.common.eventbus.EventBus;
import com.tentelemed.archipel.core.application.ApplicationEvent;
import com.tentelemed.archipel.core.application.EventRegistry;
import com.tentelemed.archipel.core.application.EventStore;
import com.tentelemed.archipel.core.application.event.AbstractDomainEvent;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.EntityId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:31
 */
public abstract class BaseCommandService {
    protected final static Logger log = LoggerFactory.getLogger(BaseCommandService.class);

    @Autowired
    EventStore eventStore;

    @Autowired
    EventBus eventBus;

    @Autowired EventRegistry registry;

    protected CmdRes genericExec(Command cmd) {
        // validation de la commande
        validate(cmd);

        // si cmd.id == null on assume que c'est une cmd de creation, il faut donc trouver le type
        // de l'agregat correspondant
        BaseAggregateRoot aggregate;
        if (cmd.id == null) {
            Class<? extends BaseAggregateRoot> aggregateClass = registry.getAggregateClassForCommand(cmd);
            aggregate = get(aggregateClass);
        } else {
            aggregate = get(cmd.id);
        }
        try {
            // parcourir les champs de type EntityId et les stocker dans la commande
            Class c = cmd.getClass();
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
            Method m = aggregate.getClass().getMethod("execute", cmd.getClass());
            CmdRes result = (CmdRes) m.invoke(aggregate, cmd);

            if (result == null) {
                throw new RuntimeException("Command result cannot be null : " + getClass().getSimpleName() + ".execute(" + cmd.getClass().getSimpleName() + ")");
            }

            if (result.events == null || result.events.size() == 0) {
                throw new RuntimeException(getClass().getSimpleName() + ".execute(" + cmd.getClass().getSimpleName() + ") must return at least one event");
            }

            // si la commande n'a pas d'id (mode creation), le premier evt doit etre un create
            if (cmd.id == null && !result.events.get(0).isCreate()) {
                throw new RuntimeException("Command " + cmd.getClass().getSimpleName() + " has null id and must return a CREATE event : " + result.events.get(0).getClass().getSimpleName());
            }

            // l'agregat doit rester valide
            result.aggregate.validate();

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


    //    protected <M extends EntityId, MM extends BaseAggregateRoot<M>> M post(MM target, Collection<DomainEvent> events) {
    protected <M extends EntityId> M post(CmdRes res) {
        eventStore.handleEvents(res);
        return (M) res.aggregate.getEntityId();
    }

    protected <I extends EntityId> void post(BaseAggregateRoot<I> target, AbstractDomainEvent... events) {
        post(new CmdRes(target, Arrays.asList(events)));
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


    // Rq : @Transactional n'a pas d'effet sur les methodes non 'public' (à moins de passer par AspectJ)
    protected <ID extends EntityId> ID _execute2(Command<ID> command) {
        Method m;
        try {
            // vérifier si handle existe bien
            Class c = command.getClass();
            while (c.getName().contains("$$") && c.getSuperclass() != null) {
                c = c.getSuperclass();
            }
            m = getClass().getDeclaredMethod("handle", c);
        } catch (NoSuchMethodException e) {
            log.error(null, e);
            throw new RuntimeException("Handle method not found for command : " + command.getClass().getSimpleName());
        }

        // valider la commande
        validate(command);

        try {
            // executer la commande
            m.setAccessible(true);
            CmdRes result = (CmdRes) m.invoke(this, command);
            return (ID) post(result);
        } catch (InvocationTargetException e) {
            log.error(null, e);
            throw new RuntimeException("Error while executing command", e.getTargetException());
        } catch (IllegalAccessException e) {
            log.error(null, e);
            throw new RuntimeException("Cannot execute handle on command : " + command.getClass().getSimpleName());
        } finally {
            if (m != null) {
                m.setAccessible(false);
            }
        }
    }

    // Rq : @Transactional n'a pas d'effet sur les methodes non 'public' (à moins de passer par AspectJ)
//    protected <ID extends EntityId> ID _execute(Command<ID> command, CommandHandler ch) {
    protected <ID extends EntityId> CmdRes _execute(Command<ID> command, CommandHandler ch) {

        // valider la commande
        validate(command);

        try {
            // executer la commande
            CmdRes result = ch.handle(command);
            if (result == null) {
                throw new RuntimeException("Command result cannot be null : " + getClass().getSimpleName() + ".execute(" + command.getClass().getSimpleName() + ")");
            }

            if (result.events == null || result.events.size() == 0) {
                throw new RuntimeException(getClass().getSimpleName() + ".execute(" + command.getClass().getSimpleName() + ") must return at least one event");
            }

            // si la commande n'a pas d'id (mode creation), le premier evt doit etre un create
            if (command.id == null && !result.events.get(0).isCreate()) {
                throw new RuntimeException("Command " + command.getClass().getSimpleName() + " has null id and must return a CREATE event : " + result.events.get(0).getClass().getSimpleName());
            }

            // l'agregat doit rester valide
            result.aggregate.validate();

            post(result);
            return result;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error(null, e);
            throw new RuntimeException("Error while running command : " + command.getClass().getSimpleName());
        }
    }


}
