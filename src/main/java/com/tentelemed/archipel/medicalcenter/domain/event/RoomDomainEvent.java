package com.tentelemed.archipel.medicalcenter.domain.event;

import com.tentelemed.archipel.core.application.event.AbstractDomainEvent;
import com.tentelemed.archipel.medicalcenter.domain.model.RoomId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 13:09
 */
public class RoomDomainEvent extends AbstractDomainEvent<RoomId> {
    public RoomDomainEvent() {
    }

    public RoomDomainEvent(RoomId id) {
        super(id);
    }
}
