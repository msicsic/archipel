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

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
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

    @Autowired EventStore eventStore;
    @Autowired EventBus eventBus;
    @Autowired EventRegistry registry;

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
