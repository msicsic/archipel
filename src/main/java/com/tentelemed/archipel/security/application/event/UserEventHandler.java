package com.tentelemed.archipel.security.application.event;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 03/12/13
 * Time: 16:49
 */
public interface UserEventHandler {
    void handle(UserDeleted event);

    void handle(UserInfoUpdated event);

    void handle(UserPasswordUpdated event);

    void handle(UserRegistered event);

    void handle(UserRoleUpdated event);
}
