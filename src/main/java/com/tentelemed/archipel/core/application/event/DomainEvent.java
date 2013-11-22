package com.tentelemed.archipel.core.application.event;

import com.tentelemed.archipel.core.application.model.EntityId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/11/13
 * Time: 17:41
 */
public interface DomainEvent<M extends EntityId> {
    enum Type {
        CREATE, UPDATE, DELETE
    }
    Type getType();
    M getAggregateId();
    boolean isUpdate();
    boolean isDelete();
    boolean isCreate();
}
