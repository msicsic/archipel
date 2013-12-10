package com.tentelemed.archipel.medicalcenter.domain.event;

import com.tentelemed.archipel.medicalcenter.domain.model.Bed;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 13:14
 */
public class RoomBedRemoved extends RoomDomainEvent {
    private Bed bed;

    public RoomBedRemoved(Bed bed) {
        this.bed = bed;
    }

    public Bed getBed() {
        return bed;
    }
}
