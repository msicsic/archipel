package com.tentelemed.archipel.medicalcenter.domain.event;

import com.tentelemed.archipel.medicalcenter.domain.model.Location;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 13:08
 */
public class RoomUpdated extends RoomDomainEvent {
    private String name;
    private boolean medical;
    private Location location;

    RoomUpdated() {
    }

    public RoomUpdated(String name, boolean medical, Location location) {
        this.name = name;
        this.medical = medical;
        this.location = location;
    }

    public Location getCode() {
        return location;
    }

    public String getName() {
        return name;
    }

    public boolean isMedical() {
        return medical;
    }
}
