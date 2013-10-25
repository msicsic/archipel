package com.tentelemed.archipel.core;

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
}
