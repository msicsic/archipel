package com.tentelemed.archipel.security.domain.model;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.security.application.command.CmdRoleCreate;
import com.tentelemed.archipel.security.application.command.CmdRoleDelete;
import com.tentelemed.archipel.security.application.command.CmdRoleUpdateRights;
import com.tentelemed.archipel.security.application.command.RoleCmdHandler;
import com.tentelemed.archipel.security.application.event.EvtRoleDeleted;
import com.tentelemed.archipel.security.application.event.EvtRoleRegistered;
import com.tentelemed.archipel.security.application.event.EvtRoleRightsUpdated;
import com.tentelemed.archipel.security.application.event.RoleEventHandler;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 03/01/14
 * Time: 14:50
 */
@RunWith(ZohhakRunner.class)
public class RoleTest implements RoleCmdHandler, RoleEventHandler {

    @Override
    @TestWith("null")
    public CmdRes execute(CmdRoleCreate cmd) {
        // Given
        cmd = new CmdRoleCreate("RoleName", Right.RIGHT_A);
        cmd.id = new RoleId(1);
        Role role = new Role();

        // When
        CmdRes res = role.execute(cmd);

        // Then
        EvtRoleRegistered evt = res.getEvent(EvtRoleRegistered.class);
        assertThat(evt, notNullValue());
        assertThat(evt.getName(), equalTo("RoleName"));
        assertThat(evt.getRights().iterator().next(), equalTo(Right.RIGHT_A));
        return null;
    }



    @Override
    @TestWith("null")
    public CmdRes execute(CmdRoleDelete cmd) {
        // ras
        return null;
    }

    @Override
    @TestWith("null")
    public CmdRes execute(CmdRoleUpdateRights cmd) {
        // Given
        Role role = new Role();
        Set<Right> rights = new HashSet<>();
        rights.add(Right.RIGHT_A);
        role.handle(new EvtRoleRegistered(new RoleId(1), "RoleName", rights));
        cmd = new CmdRoleUpdateRights(new RoleId(1), Right.RIGHT_A, Right.RIGHT_B);

        // When
        CmdRes res = role.execute(cmd);

        // Then
        EvtRoleRightsUpdated evt = res.getEvent(EvtRoleRightsUpdated.class);
        assertThat(evt, notNullValue());
        assertThat(evt.getRights().contains(Right.RIGHT_A), equalTo(true));
        assertThat(evt.getRights().contains(Right.RIGHT_B), equalTo(true));
        return null;
    }

    @Override
    @TestWith("null")
    public void handle(EvtRoleRegistered event) {
        // Given
        Role role = new Role();
        Set<Right> rights = new HashSet<>();
        rights.add(Right.RIGHT_A);
        event = new EvtRoleRegistered(new RoleId(1), "RoleName", rights);

        // When
        role.handle(event);

        // Then
        assertThat(event.processed, equalTo(true));
        assertThat(role.getEntityId(),equalTo(new RoleId(1)));
        assertThat(role.getName(), equalTo("RoleName"));
        assertThat(role.getRights().contains(Right.RIGHT_A), equalTo(true));
    }

    @Override
    @TestWith("null")
    public void handle(EvtRoleDeleted event) {
        // ras
    }

    @Override
    @TestWith("null")
    public void handle(EvtRoleRightsUpdated event) {
        // Given
        Role role = new Role();
        Set<Right> rights = new HashSet<>();
        rights.add(Right.RIGHT_A);
        EvtRoleRegistered evtCreate = new EvtRoleRegistered(new RoleId(1), "RoleName", rights);
        role.handle(evtCreate);
        Set<Right> rights2 = new HashSet<>();
        rights2.add(Right.RIGHT_B);
        event = new EvtRoleRightsUpdated(role.getEntityId(), rights2);

        // When
        role.handle(event);

        // Then
        assertThat(event.processed, equalTo(true));
        assertThat(role.getRights().contains(Right.RIGHT_B), equalTo(true));
        assertThat(role.getRights().contains(Right.RIGHT_A), equalTo(false));

    }
}
