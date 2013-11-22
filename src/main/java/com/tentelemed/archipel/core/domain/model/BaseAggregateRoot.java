package com.tentelemed.archipel.core.domain.model;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.application.model.EntityId;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 11:25
 */
public abstract class BaseAggregateRoot<M extends EntityId> extends BaseEntity<M> {

    public List<DomainEvent> list(DomainEvent... event) {
        return Arrays.asList(event);
    }

    public void _setId(String id) {
        this.id = id;
    }
}
