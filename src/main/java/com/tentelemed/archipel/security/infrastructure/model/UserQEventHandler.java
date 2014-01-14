package com.tentelemed.archipel.security.infrastructure.model;

import com.tentelemed.archipel.security.domain.pub.*;
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
        user.setFirstName(event.getFirstName());
        user.setLastName(event.getLastName());
        user.setDob(event.getDob());
        user.setEmail(event.getEmail());
    }

    @Override
    public void handle(EvtUserPasswordUpdated event) {
        user.setPassword(event.getPassword());
    }

    @Override
    public void handle(EvtUserRoleUpdated event) {
        user.setRoleId(event.getRoleId());
    }

    @Override
    public void handle(EvtUserRegistered event) {
        user.setId(event.getId().getId());
        user.setDob(event.getDob());
        user.setEmail(event.getEmail());
        user.setFirstName(event.getFirstName());
        user.setLastName(event.getLastName());
        user.setLogin(event.getCredentials().getLogin());
        user.setPassword(event.getCredentials().getPassword());
        user.setRoleId(event.getRoleId());
    }


}
