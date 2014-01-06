package com.tentelemed.archipel.security.infrastructure.model;

import com.tentelemed.archipel.security.application.event.*;
import com.tentelemed.archipel.security.application.event.EvtRoleDeleted;
import com.tentelemed.archipel.security.application.event.EvtRoleRightsUpdated;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 03/01/14
 * Time: 15:01
 */
@RunWith(Parameterized.class)
public class RoleQTest implements RoleEventHandler {
    @Test
    public void testHandleRegistered() throws Exception {

    }

    @Test
    public void testHandleRightsUpdated() throws Exception {

    }

    @Test
    @Override
    public void handle(EvtRoleRegistered event) {
        System.err.println("hop");
    }

    @Override
    public void handle(EvtRoleDeleted event) {
        // TODO
    }

    @Override
    public void handle(EvtRoleRightsUpdated event) {
        // TODO
    }
}
