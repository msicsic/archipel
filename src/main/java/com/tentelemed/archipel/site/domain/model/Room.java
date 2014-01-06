package com.tentelemed.archipel.site.domain.model;

import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.site.domain.event.EvtRoomBedAdded;
import com.tentelemed.archipel.site.domain.event.EvtRoomBedRemoved;
import com.tentelemed.archipel.site.domain.event.EvtRoomRegistered;
import com.tentelemed.archipel.site.domain.event.EvtRoomUpdated;

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
        return _result(handle(new EvtRoomRegistered(name, medical, location, new HashSet<Bed>())));
    }

    public CmdRes update(String name, Location location) {
        boolean medical = location.isMedical();
        return _result(handle(new EvtRoomUpdated(name, medical, location)));
    }

    public CmdRes addBed(Bed bed) {
        if (!isMedical()) {
            throw new RuntimeException("cannot add beds to non medical room");
        }
        return _result(handle(new EvtRoomBedAdded(bed)));
    }

    public CmdRes removeBed(Bed bed) {
        return _result(handle(new EvtRoomBedRemoved(bed)));
    }

    // EVENTS
    public EvtRoomRegistered handle(EvtRoomRegistered event) {
        return apply(event);
    }

    public EvtRoomBedAdded handle(EvtRoomBedAdded event) {
        this.beds.add(event.getBed());
        return handled(event);
    }

    public EvtRoomBedRemoved handle(EvtRoomBedRemoved event) {
        this.beds.remove(event.getBed());
        return handled(event);
    }

    public EvtRoomUpdated handle(EvtRoomUpdated event) {
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
