package com.tentelemed.archipel.site.domain.model;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.DomainException;
import com.tentelemed.archipel.site.application.command.CmdRoomAddBed;
import com.tentelemed.archipel.site.application.command.CmdRoomCreate;
import com.tentelemed.archipel.site.application.command.CmdRoomRemoveBed;
import com.tentelemed.archipel.site.application.command.RoomCmdHandler;
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
public class Room extends BaseAggregateRoot<RoomId> implements RoomCmdHandler {
    @NotNull String name;
    boolean medical;
    @NotNull @Valid Set<Bed> beds = new HashSet<>();
    @NotNull LocationPath locationPath;
    @NotNull SiteId siteId;

    @Override
    protected Class<RoomId> getIdClass() {
        return RoomId.class;
    }

    // COMMANDS
    public CmdRes execute(CmdRoomCreate cmd) {
        Site site = (Site) cmd.getData(cmd.siteId);
        if (site == null) {
            throw new DomainException("Site cannot be null");
        }
        Location location = site.getLocationFromPath(cmd.locationPath);
        if (location == null) {
            throw new DomainException("Location not found : "+cmd.locationPath);
        }
        if (cmd.medical && ! location.isMedical()) {
            throw new DomainException("Medical room must be in a medical location");
        }
        return _result(handle(new EvtRoomRegistered(getEntityId(), cmd.siteId, cmd.name, location.isMedical(), cmd.locationPath)));
    }

    public CmdRes execute(CmdRoomAddBed cmd) {
        if (! isMedical()) {
            throw new RuntimeException("cannot add beds to non medical room");
        }
        return _result(handle(new EvtRoomBedAdded(getEntityId(), cmd.bed)));
    }

    public CmdRes execute(CmdRoomRemoveBed cmd) {
        return _result(handle(new EvtRoomBedRemoved(getEntityId(), cmd.bed)));
    }

    public CmdRes update(String name, Location location) {
        boolean medical = location.isMedical();
        return _result(handle(new EvtRoomUpdated(name, medical, location)));
    }

    // EVENTS
    EvtRoomRegistered handle(EvtRoomRegistered event) {
        this.id = event.getId().getId();
        this.name = event.getName();
        this.medical = event.isMedical();
        this.siteId = event.getSiteId();
        this.locationPath = event.getLocationPath();
        return handled(event);
    }

    EvtRoomBedAdded handle(EvtRoomBedAdded event) {
        this.beds.add(event.getBed());
        return handled(event);
    }

    EvtRoomBedRemoved handle(EvtRoomBedRemoved event) {
        this.beds.remove(event.getBed());
        return handled(event);
    }

    EvtRoomUpdated handle(EvtRoomUpdated event) {
        // TODO
        return null;
    }

    // GETTERS


    public LocationPath getLocationPath() {
        return locationPath;
    }

    public SiteId getSiteId() {
        return siteId;
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
