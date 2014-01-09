package com.tentelemed.archipel.core.application;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.infrastructure.model.BaseEntityQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 11/12/13
 * Time: 15:34
 */
@Component
public class EventRegistry {
    private final static Logger log = LoggerFactory.getLogger(EventRegistry.class);

    Map<Class<? extends DomainEvent>, Class<? extends BaseAggregateRoot>> registry = new HashMap<>();
    Map<Class<? extends DomainEvent>, Class<? extends BaseEntityQ>> registryQ = new HashMap<>();
    Map<Class<? extends DomainEvent>, Class<?>> registryHandler = new HashMap<>();

    Map<Class<? extends Command>, Class<? extends BaseAggregateRoot>> mapCmd = new HashMap<>();



    public void addCmdEntry(Class<? extends Command> cmdClass, Class<? extends BaseAggregateRoot> aggregateClass) {
        mapCmd.put(cmdClass, aggregateClass);
    }

    public Class<? extends BaseAggregateRoot> getAggregateClassForCommand(Class<? extends Command> commandClass) {
        return mapCmd.get(commandClass);
    }

    public Class<? extends BaseAggregateRoot> getAggregateClassForCommand(Command cmd) {
        return mapCmd.get(cmd.getClass());
    }

    public void addEntry(
            Class<? extends DomainEvent> eventClazz,
            Class<? extends BaseAggregateRoot> clazz, Class<? extends BaseEntityQ> clazzQ,
            Class<?> evenHandlerClass

    ) {
        registry.put(eventClazz, clazz);
        registryQ.put(eventClazz, clazzQ);
        registryHandler.put(eventClazz, evenHandlerClass);
    }

    public Class<? extends BaseAggregateRoot> getClassForEvent(DomainEvent event) {
        for (Class<? extends DomainEvent> c : registry.keySet()) {
            if (c.isInstance(event)) {
                return registry.get(c);
            }
        }
        return null;
    }

    public Class<? extends BaseEntityQ> getClassQForEvent(DomainEvent event) {
        for (Class<? extends DomainEvent> c : registryQ.keySet()) {
            if (c.isInstance(event)) {
                return registryQ.get(c);
            }
        }
        return null;
    }

    public Class<?> getHandlerClassForEvent(DomainEvent event) {
        for (Class<? extends DomainEvent> c : registryHandler.keySet()) {
            if (c.isInstance(event)) {
                return registryHandler.get(c);
            }
        }
        return null;
    }

    public BaseEntityQ newEntityQ(DomainEvent event) {
        Class c = getClassQForEvent(event);
        if (c == null) {
            throw new RuntimeException("No EntityQ class found for event : " + event.getClass().getSimpleName());
        }
        Constructor cst = null;
        try {
            cst = c.getDeclaredConstructor();
            cst.setAccessible(true);
            return (BaseEntityQ) c.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Cannot instanciate EntityQ of class : " + c.getSimpleName());
        } finally {
            if (cst != null) {
                cst.setAccessible(false);
            }
        }
    }

    public BaseAggregateRoot newAggregateForEvent(DomainEvent event) {
        // un evt qui instancie un agregat doit necessairement etre de type creation
        if (! event.isCreate()) {
            throw new RuntimeException("First event must be CreateEvent");
        }

        Class c = getClassForEvent(event);
        if (c == null) {
            throw new RuntimeException("No aggregate class found for event : " + event.getClass().getSimpleName());
        }
        Constructor cst = null;
        try {
            cst = c.getDeclaredConstructor();
            cst.setAccessible(true);
            return (BaseAggregateRoot) c.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Cannot instanciate aggregate of class : " + c.getSimpleName());
        } finally {
            if (cst != null) {
                cst.setAccessible(false);
            }
        }
    }
}
