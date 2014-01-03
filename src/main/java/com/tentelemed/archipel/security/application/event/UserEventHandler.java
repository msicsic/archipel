package com.tentelemed.archipel.security.application.event;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 03/01/14
 * Time: 15:21
 */
public interface UserEventHandler {
    void handle(UserDeleted event);
    void handle(UserInfoUpdated event);
    void handle(UserPasswordUpdated event);
    void handle(UserRegistered event);
    void handle(UserRoleUpdated event);
}
