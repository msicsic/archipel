package com.tentelemed.archipel.security.domain.pub;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:10
 */
public class EvtRoleDeleted extends EvtRoleDomainEvent {

    EvtRoleDeleted() {
    }

    public EvtRoleDeleted(RoleId id) {
        super(id);
    }

    @Override
    public Type getCrudType() {
        return Type.DELETE;
    }
}
