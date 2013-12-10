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
public class RoleRegistered extends RoleDomainEvent {

    @Override
    public Type getCrudType() {
        return Type.CREATE;
    }

    private String name;
    private Set<Right> rights;

    RoleRegistered() {}

    public RoleRegistered(RoleId id, String name, Set<Right> rights) {
        super(id);
        this.name = name;
        this.rights = rights;
    }

    public String getName() {
        return name;
    }

    public Set<Right> getRights() {
        return rights;
    }
}
