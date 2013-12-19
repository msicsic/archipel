package com.tentelemed.archipel.site.domain.model;

import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.site.domain.event.*;
import com.tentelemed.archipel.site.infrastructure.model.LocationQ;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 11:40
 */
public class Site extends BaseAggregateRoot<SiteId> implements SiteEventHandler {

    @NotNull SiteType type;
    @NotNull String name;
    @NotNull String ident;
    @Valid Set<RoomId> rooms = new HashSet<>();
    @Valid SiteInfo info;
    @Valid Set<Sector> sectors = new HashSet<>();

    // COMMANDS
    public CmdRes register(SiteType type, String name, String ident) {
        validate("type", type);
        validate("name", name);
        validate("ident", ident);
        LocationQ sector = new LocationQ(LocationQ.Type.SECTOR, "Default", "MED");
        return result(new SiteRegistered(getEntityId(), type, name, ident, sector));
    }

    public CmdRes updateMainInfo(SiteType type, String name, String ident) {
        validate("type", type);
        validate("name", name);
        validate("ident", ident);
        return result(new SiteMainInfoUpdated(getEntityId(), type, name, ident));
    }

    public CmdRes updateAdditionalInfo(SiteInfo info) {
        validate("info", info);
        return result(new SiteAdditionalInfoUpdated(getEntityId(), info));
    }

    public CmdRes addRoom(RoomId room) {
        // TODO : tester l'existance de la Room
        return result(new SiteRoomAdded(getEntityId(), room));
    }

    public CmdRes removeRoom(RoomId room) {
        // TODO : tester l'existance de la Room
        return result(new SiteRoomRemoved(getEntityId(), room));
    }

    public CmdRes delete() {
        return result(new SiteDeleted(getEntityId()));
    }

    public CmdRes createService(SectorId parent, String code, String name) {
        Sector found = null;
        for (Sector sector : sectors) {
            if (sector.getEntityId().equals(parent)) {
                found = sector;
            }
        }
        if (found == null) {
            throw new RuntimeException("Sector not found");
        } else {
            Service service = new Service(found, name, code);
            return result(new SiteServiceAdded(getEntityId(), service));
        }
    }

    public CmdRes createSector(Sector.Type type, String code, String name) {
        // le type ne doit pas deja etre présent
        for (Sector s : sectors) {
            if (s.getType() == type) {
                throw new RuntimeException("This type of Sector is already present");
            }
        }
        return result(new SiteSectorAdded(getEntityId(), new SectorId(getSectors().size()+1)));
    }

    // methodes utilitaires

    public List<String> getLocationCodes() {
        List<String> result = new ArrayList<>();
        for (Sector sector : sectors) {
            result.addAll(sector.getLocationCodes());
        }
        return result;
    }



    // EVENTS


    @Override
    public void handle(SiteServiceAdded event) {
        Service service = event.getService();
        service.getParent().addService(service);
    }

    @Override
    public void handle(SiteRegistered event) {
        apply(event);
        LocationQ sector = event.getDefaultSector();
        // Sector sector = new Sector();
        // TODO : creation d'un Sector, mais changer d'abord les ID d'Entity
    }

    @Override
    public void handle(SiteMainInfoUpdated event) {
        apply(event);
    }

    @Override
    public void handle(SiteAdditionalInfoUpdated event) {
        apply(event);
    }

    @Override
    public void handle(SiteRoomAdded event) {
        rooms.add(event.getRoomId());
    }

    @Override
    public void handle(SiteRoomRemoved event) {
        rooms.remove(event.getRoomId());
    }

    @Override
    public void handle(SiteDeleted event) {
        // ras
    }

// GETTERS

    @Override
    protected Class<SiteId> getIdClass() {
        return SiteId.class;
    }

    public SiteType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getIdent() {
        return ident;
    }

    public Set<Sector> getSectors() {
        return Collections.unmodifiableSet(sectors);
    }

    public Set<RoomId> getRooms() {
        return Collections.unmodifiableSet(rooms);
    }

    public SiteInfo getInfo() {
        return info;
    }

}
