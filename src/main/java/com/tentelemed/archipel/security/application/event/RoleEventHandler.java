package com.tentelemed.archipel.security.application.event;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 03/12/13
 * Time: 16:49
 */
public interface RoleEventHandler {
    void handle(RoleRegistered event);
    void handle(RoleRightsUpdated event);
}
