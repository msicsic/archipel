package com.tentelemed.archipel.core.application;

import com.tentelemed.archipel.core.domain.model.EntityId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/10/13
 * Time: 15:18
 */
public interface DomainEvent<M extends EntityId> {
    M getId();
}
