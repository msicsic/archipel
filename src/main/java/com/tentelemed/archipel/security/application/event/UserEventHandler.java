package com.tentelemed.archipel.security.application.event;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 03/01/14
 * Time: 15:21
 */
public interface UserEventHandler {
    void handle(EvtUserDeleted event);

    void handle(EvtUserInfoUpdated event);

    void handle(EvtUserPasswordUpdated event);

    void handle(EvtUserRegistered event);

    void handle(EvtUserRoleUpdated event);
}
