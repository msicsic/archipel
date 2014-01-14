package com.tentelemed.archipel.site.domain.pub;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 13:08
 */
public class EvtRoomUpdated extends EvtRoomDomainEvent {
    private String name;
    private boolean medical;
    private Location location;

    EvtRoomUpdated() {
    }

    public EvtRoomUpdated(String name, boolean medical, Location location) {
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
