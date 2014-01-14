package com.tentelemed.archipel.site.domain.pub;

import com.tentelemed.archipel.core.domain.pub.AbstractDomainEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 13:09
 */
public class EvtRoomDomainEvent extends AbstractDomainEvent<RoomId> {
    public EvtRoomDomainEvent() {
    }

    public EvtRoomDomainEvent(RoomId id) {
        super(id);
    }
}
