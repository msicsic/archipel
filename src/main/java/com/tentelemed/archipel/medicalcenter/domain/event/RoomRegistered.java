package com.tentelemed.archipel.medicalcenter.domain.event;

import com.tentelemed.archipel.medicalcenter.domain.model.Bed;
import com.tentelemed.archipel.medicalcenter.domain.model.LocationCode;

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
    private LocationCode code;

    RoomRegistered() {
    }

    public RoomRegistered(String name, boolean medical, LocationCode code, Set<Bed> beds) {
        this.name = name;
        this.medical = medical;
        this.beds = beds;
        this.code = code;
    }

    public LocationCode getCode() {
        return code;
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
