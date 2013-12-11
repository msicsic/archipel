package com.tentelemed.archipel.core.application;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
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

    public void addEntry(Class<? extends DomainEvent> eventClazz, Class<? extends BaseAggregateRoot> clazz) {
        DomainEvent event;
        try {
            Constructor c = eventClazz.getDeclaredConstructor();
            c.setAccessible(true);
            event = (DomainEvent) c.newInstance();
        } catch (Exception e) {
            log.error(null, e);
            throw new RuntimeException("An empty constructor must be provided");
        }
        if (!event.isCreate()) {
            throw new RuntimeException("Only 'Create' events can be added to the registry");
        }
        registry.put(eventClazz, clazz);
    }

    public Class<? extends BaseAggregateRoot> getClassForEvent(DomainEvent event) {
        return registry.get(event.getClass());
    }

    public BaseAggregateRoot newAggregateForEvent(DomainEvent event) {
        Class c = registry.get(event.getClass());
        if (c == null) {
            throw new RuntimeException("No aggregate class found for event : "+event.getClass().getSimpleName());
        }
        Constructor cst = null;
        try {
            cst = c.getDeclaredConstructor();
            cst.setAccessible(true);
            return (BaseAggregateRoot) c.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Cannot instanciate aggregate of class : "+c.getSimpleName());
        } finally {
            if (cst != null) {
                cst.setAccessible(false);
            }
        }
    }
}
