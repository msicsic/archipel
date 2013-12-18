package com.tentelemed.archipel.medicalcenter.domain.event;

import com.tentelemed.archipel.medicalcenter.domain.model.Bed;
import com.tentelemed.archipel.medicalcenter.domain.model.Location;

import java.util.Collections;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 13:08
 */
public class RoomRegistered extends RoomDomainEvent {
    private String name;
    private boolean medical;
    private Set<Bed> beds;
    private Location location;

    RoomRegistered() {
    }

    public RoomRegistered(String name, boolean medical, Location location, Set<Bed> beds) {
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
