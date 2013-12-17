package com.tentelemed.archipel.medicalcenter.domain.event;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 15:02
 */
public interface RoomEventHandler {
    void handle(RoomBedAdded event);

    void handle(RoomBedRemoved event);

    void handle(RoomRegistered event);
}
