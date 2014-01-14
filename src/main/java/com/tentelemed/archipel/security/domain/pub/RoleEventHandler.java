package com.tentelemed.archipel.security.domain.pub;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 03/01/14
 * Time: 14:52
 */
public interface RoleEventHandler {

    void handle(EvtRoleRegistered event);

    void handle(EvtRoleDeleted event);

    void handle(EvtRoleRightsUpdated event);
}
