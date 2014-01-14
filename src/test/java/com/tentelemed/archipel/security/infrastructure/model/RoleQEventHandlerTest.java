package com.tentelemed.archipel.security.infrastructure.model;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import com.tentelemed.archipel.security.domain.pub.*;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 03/01/14
 * Time: 15:01
 */
@RunWith(ZohhakRunner.class)
public class RoleQEventHandlerTest implements RoleEventHandler {

    @Override
    @TestWith("null")
    public void handle(EvtRoleRegistered event) {
        // Given
        RoleQEventHandler handler = new RoleQEventHandler();
        Set<Right> rights = new HashSet<>();
        rights.add(Right.RIGHT_A);
        event = new EvtRoleRegistered(new RoleId(1), "RoleName", rights);
        RoleQ role = new RoleQ();

        // When
        handler.setObject(role);
        handler.handle(event);

        // Then
        assertThat(role.getId(), equalTo(1));
        assertThat(role.getName(), equalTo("RoleName"));
        assertThat(role.getRights().size(), equalTo(1));
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
        RoleQEventHandler handler = new RoleQEventHandler();
        Set<Right> rights2 = new HashSet<>();
        rights2.add(Right.RIGHT_B);
        Set<Right> rights1 = new HashSet<>();
        rights1.add(Right.RIGHT_A);
        RoleQ role = new RoleQ();
        role.setRights(rights1);
        event = new EvtRoleRightsUpdated(new RoleId(1), rights2);

        // When
        handler.setObject(role);
        handler.handle(event);

        // Then
        assertThat(role.getRights().size(), equalTo(1));
        assertThat(role.getRights().iterator().next(), equalTo(Right.RIGHT_B));
    }

}
