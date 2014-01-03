package com.tentelemed.archipel.security.domain.model;

import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.security.application.command.CmdHandlerRole;
import com.tentelemed.archipel.security.application.command.CmdRoleCreate;
import com.tentelemed.archipel.security.application.command.CmdRoleDelete;
import com.tentelemed.archipel.security.application.command.CmdRoleUpdateRights;
import com.tentelemed.archipel.security.application.event.RoleDeleted;
import com.tentelemed.archipel.security.application.event.RoleRegistered;
import com.tentelemed.archipel.security.application.event.RoleRightsUpdated;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 01/12/13
 * Time: 02:01
 */
public class Role extends BaseAggregateRoot<RoleId> implements CmdHandlerRole {
    @NotNull private String name;
    @NotNull private Set<Right> rights = new HashSet<>();

    // *********** COMMANDS **********************
    // *********** COMMANDS **********************
    // *********** COMMANDS **********************

    public CmdRes execute(CmdRoleCreate cmd) {
        validate("name", cmd.name);
        return _result(handle(new RoleRegistered(getEntityId(), cmd.name, cmd.rights)));
    }

    public CmdRes execute(CmdRoleDelete cmd) {
        return _result(handle(new RoleDeleted(getEntityId())));
    }

    public CmdRes execute(CmdRoleUpdateRights cmd) {
        return _result(handle(new RoleRightsUpdated(getEntityId(), cmd.rights)));
    }

    // *********** EVENTS ************************
    // *********** EVENTS ************************
    // *********** EVENTS ************************

    RoleDeleted handle(RoleDeleted roleDeleted) {
        return handled(roleDeleted);
    }

    RoleRegistered handle(RoleRegistered event) {
        this.name = event.getName();
        this.rights = event.getRights();
        return handled(event);
    }

    RoleRightsUpdated handle(RoleRightsUpdated event) {
        this.rights = event.getRights();
        return handled(event);
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
