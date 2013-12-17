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
public class RoleRightsUpdated extends RoleDomainEvent {

    private Set<Right> rights;

    RoleRightsUpdated() {
    }

    public RoleRightsUpdated(RoleId id, Set<Right> rights) {
        super(id);
        this.rights = rights;
    }

    public Set<Right> getRights() {
        return rights;
    }
}
