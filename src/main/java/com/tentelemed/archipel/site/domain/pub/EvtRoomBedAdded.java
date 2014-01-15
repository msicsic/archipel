package com.tentelemed.archipel.site.domain.pub;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 13:14
 */
public class EvtRoomBedAdded extends EvtRoomDomainEvent {
    private Bed bed;

    EvtRoomBedAdded() {
    }

    public EvtRoomBedAdded(RoomId id, Bed bed) {
        super(id);
        this.bed = bed;
    }

    public Bed getBed() {
        return bed;
    }
}
