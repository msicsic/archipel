package com.tentelemed.archipel.core.application;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.application.model.EntityId;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/11/13
 * Time: 12:12
 */
public class QueryUpdateEvent {
    EntityId id;
    BaseAggregateRoot aggregate;
    Collection<? extends DomainEvent> events;
    Class<? extends BaseAggregateRoot> clazz;

    public QueryUpdateEvent(Class<? extends BaseAggregateRoot> clazz, EntityId id, BaseAggregateRoot aggregate, Collection<? extends DomainEvent> events) {
        this.clazz = clazz;
        this.id = id;
        this.aggregate = aggregate;
        this.events = events;
    }

    public Class<? extends BaseAggregateRoot> getClazz() {
        return clazz;
    }

    public BaseAggregateRoot getAggregate() {
        return aggregate;
    }

    public Collection<? extends DomainEvent> getEvents() {
        return events;
    }

    public EntityId getId() {
        return id;
    }
}
