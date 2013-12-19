package com.tentelemed.archipel.site.domain.event;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 15:01
 */
public interface SiteEventHandler {
    void handle(SiteRegistered event);

    void handle(SiteMainInfoUpdated event);

    void handle(SiteAdditionalInfoUpdated event);

    void handle(SiteRoomAdded event);

    void handle(SiteRoomRemoved event);

    void handle(SiteDeleted event);

    void handle(SiteServiceAdded event);
}
