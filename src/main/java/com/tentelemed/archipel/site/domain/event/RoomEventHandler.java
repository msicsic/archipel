package com.tentelemed.archipel.site.domain.event;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 06/01/14
 * Time: 13:59
 */
public interface RoomEventHandler {
    void handle(EvtRoomBedAdded evt);

    void handle(EvtRoomBedRemoved evt);

    void handle(EvtRoomRegistered evt);

    void handle(EvtRoomUpdated evt);
}
