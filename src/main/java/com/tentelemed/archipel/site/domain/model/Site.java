package com.tentelemed.archipel.site.domain.model;

import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.DomainException;
import com.tentelemed.archipel.site.domain.event.*;
import com.tentelemed.archipel.site.infrastructure.model.LocationQ;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 11:40
 */
public class Site extends BaseAggregateRoot<SiteId> {

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
        return _result(handle(new SiteRegistered(getEntityId(), type, name, ident, sector)));
    }

    public CmdRes updateMainInfo(SiteType type, String name, String ident) {
        validate("type", type);
        validate("name", name);
        validate("ident", ident);
        return _result(handle(new SiteMainInfoUpdated(getEntityId(), type, name, ident)));
    }

    public CmdRes updateAdditionalInfo(SiteInfo info) {
        validate("info", info);
        return _result(handle(new SiteAdditionalInfoUpdated(getEntityId(), info)));
    }

    public CmdRes addRoom(RoomId room) {
        // TODO : tester l'existance de la Room
        return _result(handle(new SiteRoomAdded(getEntityId(), room)));
    }

    public CmdRes removeRoom(RoomId room) {
        // TODO : tester l'existance de la Room
        return _result(handle(new SiteRoomRemoved(getEntityId(), room)));
    }

    public CmdRes delete() {
        return _result(handle(new SiteDeleted(getEntityId())));
    }

    public CmdRes createService(String sectorCode, String code, String name) {
        if (getSectorCodes().contains(code)) {
            throw new DomainException("This code is allready used !");
        }
        Sector found = null;
        for (Sector sector : sectors) {
            if (sector.getCode().equals(sectorCode)) {
                found = sector;
            }
        }
        if (found == null) {
            throw new DomainException("Sector not found");
        } else {
            return _result(handle(new SiteServiceAdded(getEntityId(), sectorCode, code, name)));
        }
    }

    public CmdRes createSector(Sector.Type type, String code, String name) {
        // le type ne doit pas deja etre présent
        if (getSectorCodes().contains(code)) {
            throw new DomainException("This code is allready used !");
        }
        for (Sector s : sectors) {
            if (s.getType() == type) {
                throw new DomainException("This type of Sector is already present");
            }
        }
        return _result(handle(new SiteSectorAdded(getEntityId(), type, code, name)));
    }

    private Set<String> getSectorCodes() {
        Set<String> codes = new HashSet<>();
        for (Sector sector : sectors) {
            codes.add(sector.code);
            for (Service service : sector.getServices()) {
                codes.add(service.code);
                for (FunctionalUnit fu : service.getUnits()) {
                    codes.add(fu.code);
                    for (ActivityUnit au : fu.getUnits()) {
                        codes.add(au.code);
                    }
                }
            }
        }
        return codes;
    }

    public CmdRes deleteSector(String sectorCode) {
        for (Sector sector : sectors) {
            if (sector.getCode().equals(sectorCode)) {
                return _result(handle(new SiteSectorDeleted(getEntityId(), sectorCode)));
            }
        }
        throw new DomainException("this SectorCode is not present in this Site");
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

    SiteSectorDeleted handle(SiteSectorDeleted event) {
        for (Sector sector : sectors) {
            if (sector.getCode().equals(event.getSectorCode())) {
                sectors.remove(sector);
                break;
            }
        }
        return handled(event);
    }

    SiteSectorAdded handle(SiteSectorAdded event) {
        Sector sector = new Sector(event.getSectorType(), event.getSectorName(), event.getSectorCode());
        sectors.add(sector);
        return handled(event);
    }

    SiteServiceAdded handle(SiteServiceAdded event) {
        for (Sector sector : sectors) {
            if (sector.getCode().equals(event.getParent())) {
                new Service(sector, event.getName(), event.getCode());
            }
        }
        return handled(event);
    }

    SiteRegistered handle(SiteRegistered event) {
        apply(event);
        LocationQ sectorQ = event.getDefaultSector();
        Sector sector = new Sector(sectorQ.getSectorType(), sectorQ.getName(), sectorQ.getCode());
        sectors.add(sector);
        return handled(event);
    }

    SiteMainInfoUpdated handle(SiteMainInfoUpdated event) {
        return apply(event);
    }

    SiteAdditionalInfoUpdated handle(SiteAdditionalInfoUpdated event) {
        return apply(event);
    }

    SiteRoomAdded handle(SiteRoomAdded event) {
        rooms.add(event.getRoomId());
        return handled(event);
    }

    SiteRoomRemoved handle(SiteRoomRemoved event) {
        rooms.remove(event.getRoomId());
        return handled(event);
    }

    SiteDeleted handle(SiteDeleted event) {
        // ras
        return event;
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
