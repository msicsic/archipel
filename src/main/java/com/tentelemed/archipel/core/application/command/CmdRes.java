package com.tentelemed.archipel.core.application.command;

import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.pub.AbstractDomainEvent;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 17/12/13
 * Time: 11:01
 */
public class CmdRes {
    public CmdRes(BaseAggregateRoot aggregate, List<AbstractDomainEvent> events) {
        this.aggregate = aggregate;
        this.events = events;
    }

    public List<AbstractDomainEvent> events;
    public BaseAggregateRoot aggregate;

    public <C extends AbstractDomainEvent> C getEvent(Class<C> eventClass) {
        for (AbstractDomainEvent event : events) {
            if (eventClass.isInstance(event)) {
                return (C) event;
            }
        }
        return null;
    }
}
