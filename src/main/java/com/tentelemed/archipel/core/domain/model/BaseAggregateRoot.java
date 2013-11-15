package com.tentelemed.archipel.core.domain.model;

import com.tentelemed.archipel.core.application.DomainEvent;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 11:25
 */
public abstract class BaseAggregateRoot<M extends EntityId> extends BaseEntity<M> {

    BaseAggregateRoot(){}

    protected BaseAggregateRoot(Class<M> idClass) {
        this.idClass = idClass;
    }

    public List<DomainEvent<M>> list(DomainEvent<M>... event) {
        return Arrays.asList(event);
    }
}
