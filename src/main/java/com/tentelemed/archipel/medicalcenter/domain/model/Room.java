package com.tentelemed.archipel.medicalcenter.domain.model;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.medicalcenter.domain.event.RoomBedAdded;
import com.tentelemed.archipel.medicalcenter.domain.event.RoomBedRemoved;
import com.tentelemed.archipel.medicalcenter.domain.event.RoomEventHandler;
import com.tentelemed.archipel.medicalcenter.domain.event.RoomRegistered;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 12:11
 */
public class Room extends BaseAggregateRoot<RoomId> implements RoomEventHandler {
    @NotNull String name;
    boolean medical;
    @NotNull @Valid Set<Bed> beds = new HashSet<>();
    @NotNull LocationCode locationCode;

    @Override
    protected Class<RoomId> getIdClass() {
        return RoomId.class;
    }

    // COMMANDS
    public List<DomainEvent> register(String name, boolean medical, LocationCode code, Set<Bed> beds) {
        if (! medical && (beds != null || beds.size() > 0)) {
            throw new RuntimeException("cannot add beds to non medical room");
        }
        return list(new RoomRegistered(name, medical, code, beds));
    }

    public List<DomainEvent> addBed(Bed bed) {
        if (! isMedical()) {
            throw new RuntimeException("cannot add beds to non medical room");
        }
        return list(new RoomBedAdded(bed));
    }

    public List<DomainEvent> removeBed(Bed bed) {
        return list(new RoomBedRemoved(bed));
    }

    // EVENTS
    public void handle(RoomRegistered event) {
        apply(event);
    }

    public void handle(RoomBedAdded event) {
        this.beds.add(event.getBed());
    }

    public void handle(RoomBedRemoved event) {
        this.beds.remove(event.getBed());
    }

    // GETTERS


    public LocationCode getLocationCode() {
        return locationCode;
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
