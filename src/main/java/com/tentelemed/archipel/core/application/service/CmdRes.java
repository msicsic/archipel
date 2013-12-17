package com.tentelemed.archipel.core.application.service;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 17/12/13
 * Time: 11:01
 */
public class CmdRes {
    public CmdRes(BaseAggregateRoot aggregate, List<DomainEvent> events) {
        this.aggregate = aggregate;
        this.events = events;
    }

    public List<DomainEvent> events;
    public BaseAggregateRoot aggregate;
}
