package com.tentelemed.archipel.security.application.event;

import com.tentelemed.archipel.security.domain.model.RoleId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:10
 */
public class RoleDeleted extends RoleDomainEvent {

    RoleDeleted() {
    }

    public RoleDeleted(RoleId id) {
        super(id);
    }

    @Override
    public Type getCrudType() {
        return Type.DELETE;
    }
}
