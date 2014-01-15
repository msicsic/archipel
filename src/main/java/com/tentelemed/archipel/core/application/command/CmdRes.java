package com.tentelemed.archipel.core.application.command;

import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.EntityId;
import com.tentelemed.archipel.core.domain.pub.AbstractDomainEvent;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 17/12/13
 * Time: 11:01
 */
public class CmdRes {
    public List<AbstractDomainEvent> events;
    public EntityId entityId;

    public CmdRes(EntityId entityId, List<AbstractDomainEvent> events) {
        this.entityId = entityId;
        this.events = events;
    }

    public <C extends AbstractDomainEvent> C getEvent(Class<C> eventClass) {
        for (AbstractDomainEvent event : events) {
            if (eventClass.isInstance(event)) {
                return (C) event;
            }
        }
        return null;
    }
}
