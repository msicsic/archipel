package com.tentelemed.archipel.security.application.event;

import com.tentelemed.archipel.security.domain.model.UserId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:10
 */
public class UserPasswordUpdated extends UserDomainEvent {
    private String password;

    UserPasswordUpdated() {
    }

    public UserPasswordUpdated(UserId id, String newPassword) {
        super(id);
        this.password = newPassword;
    }

    public String getPassword() {
        return password;
    }
}
