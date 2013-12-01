package com.tentelemed.archipel.core.application.event;

import com.tentelemed.archipel.core.domain.model.EntityId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 21/11/13
 * Time: 15:21
 */
public abstract class AbstractDomainEvent<M extends EntityId> implements DomainEvent<M> {
    protected M id;

    protected AbstractDomainEvent() {
    }

    protected AbstractDomainEvent(M id) {
        this.id = id;
    }

    @Override
    public M getAggregateId() {
        return id;
    }

    @Override
    public boolean isUpdate() {
        return getType().equals(Type.UPDATE);
    }

    @Override
    public boolean isDelete() {
        return getType().equals(Type.DELETE);
    }

    @Override
    public boolean isCreate() {
        return getType().equals(Type.CREATE);
    }

    @Override public Type getType() {
        return Type.UPDATE;
    }
}
