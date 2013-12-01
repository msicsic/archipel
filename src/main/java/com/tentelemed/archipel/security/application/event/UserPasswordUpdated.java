package com.tentelemed.archipel.security.application.event;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:10
 */
public class UserPasswordUpdated extends UserDomainEvent {
    String password;

    public UserPasswordUpdated(String newPassword) {
        this.password = newPassword;
    }

    public String getPassword() {
        return password;
    }
}
