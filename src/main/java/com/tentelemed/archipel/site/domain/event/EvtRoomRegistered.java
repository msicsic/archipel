package com.tentelemed.archipel.site.domain.event;

import com.tentelemed.archipel.site.domain.model.Bed;
import com.tentelemed.archipel.site.domain.model.Location;

import java.util.Collections;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 13:08
 */
public class EvtRoomRegistered extends EvtRoomDomainEvent {
    private String name;
    private boolean medical;
    private Set<Bed> beds;
    private Location location;

    EvtRoomRegistered() {
    }

    public EvtRoomRegistered(String name, boolean medical, Location location, Set<Bed> beds) {
        this.name = name;
        this.medical = medical;
        this.beds = beds;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public boolean isMedical() {
        return medical;
    }

    public Set<Bed> getBeds() {
        return Collections.unmodifiableSet(beds);
    }

}
