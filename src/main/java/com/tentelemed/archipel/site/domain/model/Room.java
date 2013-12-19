package com.tentelemed.archipel.site.domain.model;

import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.site.domain.event.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 12:11
 */
public class Room extends BaseAggregateRoot<RoomId> {
    @NotNull String name;
    boolean medical;
    @NotNull @Valid Set<Bed> beds = new HashSet<>();
    @NotNull Location location;

    @Override
    protected Class<RoomId> getIdClass() {
        return RoomId.class;
    }

    // COMMANDS
    public CmdRes register(String name, Location location) {
        boolean medical = location.isMedical();
        return _result(handle(new RoomRegistered(name, medical, location, new HashSet<Bed>())));
    }

    public CmdRes update(String name, Location location) {
        boolean medical = location.isMedical();
        return _result(handle(new RoomUpdated(name, medical, location)));
    }

    public CmdRes addBed(Bed bed) {
        if (!isMedical()) {
            throw new RuntimeException("cannot add beds to non medical room");
        }
        return _result(handle(new RoomBedAdded(bed)));
    }

    public CmdRes removeBed(Bed bed) {
        return _result(handle(new RoomBedRemoved(bed)));
    }

    // EVENTS
    public RoomRegistered handle(RoomRegistered event) {
        return apply(event);
    }

    public RoomBedAdded handle(RoomBedAdded event) {
        this.beds.add(event.getBed());
        return handled(event);
    }

    public RoomBedRemoved handle(RoomBedRemoved event) {
        this.beds.remove(event.getBed());
        return handled(event);
    }

    public RoomUpdated handle(RoomUpdated event) {
        // TODO
        return null;
    }

    // GETTERS

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
