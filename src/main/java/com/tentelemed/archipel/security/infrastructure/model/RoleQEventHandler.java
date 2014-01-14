package com.tentelemed.archipel.security.infrastructure.model;

import com.tentelemed.archipel.security.domain.pub.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 06/01/14
 * Time: 15:31
 */
@Component
@Scope("prototype")
public class RoleQEventHandler implements RoleEventHandler {

    RoleQ role;

    public void setObject(Object o) {
        this.role = (RoleQ) o;
    }

    @Override
    public void handle(EvtRoleRegistered event) {
        role.setId(event.getId().getId());
        role.setName(event.getName());
        role.setRights(event.getRights());
    }

    @Override
    public void handle(EvtRoleDeleted event) {
        // ras
    }

    @Override
    public void handle(EvtRoleRightsUpdated event) {
        role.setRights(event.getRights());
    }

}
