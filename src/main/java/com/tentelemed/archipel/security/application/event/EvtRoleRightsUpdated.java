package com.tentelemed.archipel.security.application.event;

import com.tentelemed.archipel.security.domain.model.Right;
import com.tentelemed.archipel.security.domain.model.RoleId;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:10
 */
public class EvtRoleRightsUpdated extends EvtRoleDomainEvent {

    private Set<Right> rights;

    EvtRoleRightsUpdated() {
    }

    public EvtRoleRightsUpdated(RoleId id, Set<Right> rights) {
        super(id);
        this.rights = rights;
    }

    public Set<Right> getRights() {
        return rights;
    }
}
