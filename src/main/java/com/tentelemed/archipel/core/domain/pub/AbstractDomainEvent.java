package com.tentelemed.archipel.core.domain.pub;

import com.tentelemed.archipel.core.domain.model.EntityId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 21/11/13
 * Time: 15:21
 */
public abstract class AbstractDomainEvent<M extends EntityId> implements DomainEvent<M> {
    private M id;

    public transient boolean processed;

    protected AbstractDomainEvent() {
    }

    protected AbstractDomainEvent(M id) {
        this.id = id;
    }

    @Override
    public M getId() {
        return id;
    }

    @Override
    public boolean isUpdate() {
        return getCrudType().equals(Type.UPDATE);
    }

    @Override
    public boolean isDelete() {
        return getCrudType().equals(Type.DELETE);
    }

    @Override
    public boolean isCreate() {
        return getCrudType().equals(Type.CREATE);
    }

    @Override
    public Type getCrudType() {
        return Type.UPDATE;
    }
}
