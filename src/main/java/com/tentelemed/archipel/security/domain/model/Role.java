package com.tentelemed.archipel.security.domain.model;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.security.application.event.RoleEventHandler;
import com.tentelemed.archipel.security.application.event.RoleRegistered;
import com.tentelemed.archipel.security.application.event.RoleRightsUpdated;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 01/12/13
 * Time: 02:01
 */
public class Role extends BaseAggregateRoot<RoleId> implements RoleEventHandler {
    @NotNull private String name;
    @NotNull private Set<Right> rights = new HashSet<>();

    // *********** COMMANDS **********************
    // *********** COMMANDS **********************
    // *********** COMMANDS **********************

    public Collection<DomainEvent> register(String name, Set<Right> rights) {
        validate("name", name);
        return list(new RoleRegistered(getEntityId(), name, rights));
    }

    public Collection<DomainEvent> updateRights(Set<Right> rights) {
        return list(new RoleRightsUpdated(getEntityId(), rights));
    }

    // *********** EVENTS ************************
    // *********** EVENTS ************************
    // *********** EVENTS ************************

    public void handle(RoleRegistered event) {
        this.name = event.getName();
        this.rights = event.getRights();
    }

    public void handle(RoleRightsUpdated event) {
        this.rights = event.getRights();
    }

    // *********** GETTERS ***********************
    // *********** GETTERS ***********************
    // *********** GETTERS ***********************

    public Set<Right> getRights() {
        return Collections.unmodifiableSet(rights);
    }

    public String getName() {
        return name;
    }

    @Override
    protected Class<RoleId> getIdClass() {
        return RoleId.class;
    }


}
