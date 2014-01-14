package com.tentelemed.archipel.site.domain.pub;


/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class EvtSiteRoomAdded extends EvtSiteDomainEvent {
    RoomId roomId;

    EvtSiteRoomAdded() {
    }

    public EvtSiteRoomAdded(SiteId id, RoomId roomId) {
        super(id);
        this.roomId = roomId;
    }

    public RoomId getRoomId() {
        return roomId;
    }
}
