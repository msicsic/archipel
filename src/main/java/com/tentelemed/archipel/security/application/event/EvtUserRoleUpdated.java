package com.tentelemed.archipel.security.application.event;

import com.tentelemed.archipel.security.domain.model.RoleId;
import com.tentelemed.archipel.security.domain.model.UserId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:10
 */
public class EvtUserRoleUpdated extends EvtUserDomainEvent {
    private RoleId roleId;

    public EvtUserRoleUpdated() {
    }

    public EvtUserRoleUpdated(UserId id, RoleId roleId) {
        super(id);
        this.roleId = roleId;
    }

    public RoleId getRoleId() {
        return roleId;
    }
}
