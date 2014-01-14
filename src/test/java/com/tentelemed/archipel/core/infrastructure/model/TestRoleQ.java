package com.tentelemed.archipel.core.infrastructure.model;

import com.tentelemed.archipel.security.domain.pub.RoleId;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 08/12/13
 * Time: 21:00
 */
public class TestRoleQ extends BaseEntityQ<RoleId> {

    @NotNull private String name;
    @NotNull private Set<String> rights = new HashSet<>();

    @Override
    protected Class<RoleId> getIdClass() {
        return RoleId.class;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getRights() {
        return rights;
    }

    public void setRights(Set<String> rights) {
        this.rights = rights;
    }
}
