package com.tentelemed.archipel.security.domain.pub;

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
