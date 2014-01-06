package com.tentelemed.archipel.security.infrastructure.model;

import com.tentelemed.archipel.security.application.event.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 06/01/14
 * Time: 15:33
 */
@Component
@Scope("prototype")
public class UserQEventHandler implements UserEventHandler {
    UserQ user;

    public void setObject(Object o) {
        this.user = (UserQ) o;
    }


    @Override
    public void handle(EvtUserDeleted event) {
        // ras
    }

    @Override
    public void handle(EvtUserInfoUpdated event) {
        user.firstName = event.getFirstName();
        user.lastName = event.getLastName();
        user.dob = event.getDob();
        user.email = event.getEmail();
    }

    @Override
    public void handle(EvtUserPasswordUpdated event) {
        user.password = event.getPassword();
    }

    @Override
    public void handle(EvtUserRoleUpdated event) {
        user.roleId = event.getRoleId();
    }

    @Override
    public void handle(EvtUserRegistered event) {
        user.setId(event.getId().getId());
        user.dob = event.getDob();
        user.email = event.getEmail();
        user.firstName = event.getFirstName();
        user.lastName = event.getLastName();
        user.login = event.getCredentials().getLogin();
        user.password = event.getCredentials().getPassword();
        user.roleId = event.getRoleId();
    }


}
