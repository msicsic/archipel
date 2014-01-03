package com.tentelemed.archipel.security.application.event;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 03/01/14
 * Time: 14:52
 */
public interface RoleEventHandler {
    void handle(RoleRegistered event);
    void handle(RoleDeleted event);
    void handle(RoleRightsUpdated event);
}
