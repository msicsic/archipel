package com.tentelemed.archipel.core.domain.model;

import com.tentelemed.archipel.core.application.event.DomainEvent;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 11:25
 */
public abstract class BaseAggregateRoot<M extends EntityId> extends BaseEntity<M> {

    protected List<DomainEvent> list(DomainEvent... events) {
        for (DomainEvent event : events) {
            handle(event);
        }
        return Arrays.asList(events);
    }

    public void _setId(String id) {
        this.id = id;
    }

    <M extends DomainEvent> M handle(M event) {
        try {
            Method method = getClass().getMethod("handle", event.getClass());
            method.invoke(this, event);
            return event;
        } catch (Exception e) {
            log.error(null, e);
            return null;
        }
    }

}
