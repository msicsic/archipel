package com.tentelemed.archipel.site.domain.event;


import com.tentelemed.archipel.site.domain.model.SiteId;
import com.tentelemed.archipel.site.domain.model.RoomId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class SiteRoomAdded extends SiteDomainEvent {
    RoomId roomId;

    SiteRoomAdded() {
    }

    public SiteRoomAdded(SiteId id, RoomId roomId) {
        super(id);
        this.roomId = roomId;
    }

    public RoomId getRoomId() {
        return roomId;
    }
}
