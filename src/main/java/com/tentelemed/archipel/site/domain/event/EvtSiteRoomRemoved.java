package com.tentelemed.archipel.site.domain.event;


import com.tentelemed.archipel.site.domain.model.RoomId;
import com.tentelemed.archipel.site.domain.model.SiteId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class EvtSiteRoomRemoved extends EvtSiteDomainEvent {
    RoomId roomId;

    EvtSiteRoomRemoved() {
    }

    public EvtSiteRoomRemoved(SiteId id, RoomId roomId) {
        super(id);
        this.roomId = roomId;
    }

    public RoomId getRoomId() {
        return roomId;
    }
}
