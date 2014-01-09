package com.tentelemed.archipel.site.domain.event;

import com.tentelemed.archipel.site.domain.model.Bed;
import com.tentelemed.archipel.site.domain.model.RoomId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 13:14
 */
public class EvtRoomBedAdded extends EvtRoomDomainEvent {
    private Bed bed;

    public EvtRoomBedAdded(RoomId id, Bed bed) {
        super(id);
        this.bed = bed;
    }

    public Bed getBed() {
        return bed;
    }
}
