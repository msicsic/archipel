package com.tentelemed.archipel.site.domain.model;

import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.domain.model.Address;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.DomainException;
import com.tentelemed.archipel.site.application.command.*;
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
public class Site extends BaseAggregateRoot<SiteId> implements SiteCmdHandler {

    @NotNull SiteType type;
    @NotNull String name;
    @NotNull String ident;
    @Valid Set<RoomId> rooms = new HashSet<>();
    @Valid SiteInfo info;
    @Valid Set<Sector> sectors = new HashSet<>();

    // COMMANDS
    @Override
    public CmdRes execute(CmdSiteCreate cmd) {
        validate("type", cmd.type);
        validate("name", cmd.name);
        validate("ident", cmd.ident);
        LocationQ sector = new LocationQ(getEntityId(), LocationQ.Type.SECTOR, "Default", "MED", null);
        return _result(handle(new EvtSiteRegistered(getEntityId(), cmd.type, cmd.name, cmd.ident, sector)));
    }

    @Override
    public CmdRes execute(CmdSiteUpdate cmd) {
        validate("type", cmd.type);
        validate("name", cmd.name);
        validate("ident", cmd.ident);
        return _result(handle(new EvtSiteMainInfoUpdated(getEntityId(), cmd.type, cmd.name, cmd.ident)));
    }

    @Override
    public CmdRes execute(CmdSiteUpdateAdditionalInfo cmd) {
        Address address = new Address(cmd.street, cmd.postalCode, cmd.town, cmd.countryIso);
        SiteInfo info = new SiteInfo(
                cmd.siret,
                address,
                cmd.phone,
                cmd.fax,
                cmd.directorName,
                cmd.bankCode,
                cmd.emergenciesAvailable,
                cmd.drugstoreAvailable,
                cmd.privateRoomAvailable
        );
        validate("info", info);
        return _result(handle(new EvtSiteAdditionalInfoUpdated(getEntityId(), info)));
    }

    @Override
    public CmdRes execute(CmdSiteDelete cmd) {
        return _result(handle(new EvtSiteDeleted(getEntityId())));
    }

    public CmdRes execute(CmdSiteCreateFunctionalUnit cmd) {
        if (getSectorCodes().contains(cmd.code)) {
            throw new DomainException("This code is allready used !");
        }
        Service found = null;
        for (Sector sector : sectors) {
            for (Service service : sector.getServices()) {
                if (service.getCode().equals(cmd.parent)) {
                    found = service;
                    break;
                }
            }
        }
        if (found == null) {
            throw new DomainException("Service not found");
        } else {
            return _result(handle(new EvtSiteFunctionalUnitAdded(getEntityId(), cmd.parent, cmd.code, cmd.name)));
        }
    }

    public CmdRes execute(CmdSiteCreateActivityUnit cmd) {
        if (getSectorCodes().contains(cmd.code)) {
            throw new DomainException("This code is allready used !");
        }
        Service found = null;
        for (Sector sector : sectors) {
            for (Service service : sector.getServices()) {
                for (FunctionalUnit unit : service.getUnits()) {
                    if (unit.getCode().equals(cmd.parent)) {
                        found = service;
                        break;
                    }
                }
            }
        }
        if (found == null) {
            throw new DomainException("FunctionalUnit not found");
        } else {
            return _result(handle(new EvtSiteActivityUnitAdded(getEntityId(), cmd.parent, cmd.code, cmd.name)));
        }
    }

    @Override
    public CmdRes execute(CmdSiteCreateService cmd) {
        if (getSectorCodes().contains(cmd.code)) {
            throw new DomainException("This code is allready used !");
        }
        Sector found = null;
        for (Sector sector : sectors) {
            if (sector.getCode().equals(cmd.sectorCode)) {
                found = sector;
                break;
            }
        }
        if (found == null) {
            throw new DomainException("Sector not found");
        } else {
            return _result(handle(new EvtSiteServiceAdded(getEntityId(), cmd.sectorCode, cmd.code, cmd.name)));
        }
    }

    @Override
    public CmdRes execute(CmdSiteCreateSector cmd) {
        // le type ne doit pas deja etre présent
        if (getSectorCodes().contains(cmd.code)) {
            throw new DomainException("This code is allready used !");
        }
        for (Sector s : sectors) {
            if (s.getType() == cmd.type) {
                throw new DomainException("This type of Sector is already present");
            }
        }
        return _result(handle(new EvtSiteSectorAdded(getEntityId(), cmd.type, cmd.code, cmd.name)));
    }

    @Override
    public CmdRes execute(CmdSiteDeleteSector cmd) {
        for (Sector sector : sectors) {
            if (sector.getCode().equals(cmd.code)) {
                if (sector.getType().equals(Sector.Type.MED)) {
                    throw new DomainException("Default sector (MED) cannot be deleted");
                }
                return _result(handle(new EvtSiteSectorDeleted(getEntityId(), cmd.code)));
            }
        }
        throw new DomainException("this SectorCode is not present in this Site");
    }

    @Override
    public CmdRes execute(CmdSiteDeleteService cmd) {
        for (Sector sector : sectors) {
            for (Service service : sector.getServices()) {
                if (service.getCode().equals(cmd.code)) {
                    return _result(handle(new EvtSiteServiceDeleted(getEntityId(), cmd.code)));
                }
            }
        }
        throw new DomainException("this SectorCode is not present in this Site");
    }

    public CmdRes execute(CmdSiteDeleteFunctionalUnit cmd) {
        for (Sector sector : sectors) {
            for (Service service : sector.getServices()) {
                for (FunctionalUnit fu : service.getUnits()) {
                    if (fu.getCode().equals(cmd.code)) {
                        return _result(handle(new EvtSiteFunctionalUnitDeleted(getEntityId(), cmd.code)));
                    }
                }
            }
        }
        throw new DomainException("this FunctionalUnitCode is not present in this Site");
    }

    public CmdRes execute(CmdSiteDeleteActivityUnit cmd) {
        for (Sector sector : sectors) {
            for (Service service : sector.getServices()) {
                for (FunctionalUnit fu : service.getUnits()) {
                    for (ActivityUnit au : fu.getUnits()) {
                        if (au.getCode().equals(cmd.code)) {
                            return _result(handle(new EvtSiteActivityUnitDeleted(getEntityId(), cmd.code)));
                        }
                    }
                }
            }
        }
        throw new DomainException("this ActivityUnitCode is not present in this Site");
    }

    // methodes utilitaires

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

    public List<String> getLocationCodes() {
        List<String> result = new ArrayList<>();
        for (Sector sector : sectors) {
            result.addAll(sector.getLocationCodes());
        }
        return result;
    }

    // EVENTS

    EvtSiteSectorAdded handle(EvtSiteSectorAdded event) {
        Sector sector = new Sector(event.getSectorType(), event.getSectorName(), event.getSectorCode());
        sectors.add(sector);
        return handled(event);
    }

    EvtSiteSectorDeleted handle(EvtSiteSectorDeleted event) {
        for (Sector sector : sectors) {
            if (sector.getCode().equals(event.getSectorCode())) {
                sectors.remove(sector);
                break;
            }
        }
        return handled(event);
    }

    EvtSiteServiceAdded handle(EvtSiteServiceAdded event) {
        for (Sector sector : sectors) {
            if (sector.getCode().equals(event.getParent())) {
                new Service(sector, event.getName(), event.getCode());
            }
        }
        return handled(event);
    }

    EvtSiteServiceDeleted handle(EvtSiteServiceDeleted event) {
        for (Sector sector : sectors) {
            for (Service service : sector.getServices()) {
                if (service.getCode().equals(event.getServiceCode())) {
                    sector.remove(service);
                    break;
                }
            }
        }
        return handled(event);
    }

    EvtSiteFunctionalUnitAdded handle(EvtSiteFunctionalUnitAdded event) {
        for (Sector sector : sectors) {
            for (Service service : sector.getServices()) {
                if (service.getCode().equals(event.getParent())) {
                    new FunctionalUnit(service, event.getName(), event.getCode());
                }
            }
        }
        return handled(event);
    }

    EvtSiteFunctionalUnitDeleted handle(EvtSiteFunctionalUnitDeleted event) {
        for (Sector sector : sectors) {
            for (Service service : sector.getServices()) {
                for (FunctionalUnit fu : service.getUnits()) {
                    if (fu.getCode().equals(event.getCode())) {
                        service.remove(fu);
                        break;
                    }
                }
            }
        }
        return handled(event);
    }

    EvtSiteActivityUnitAdded handle(EvtSiteActivityUnitAdded event) {
        for (Sector sector : sectors) {
            for (Service service : sector.getServices()) {
                for (FunctionalUnit fu : service.getUnits()) {
                    if (fu.getCode().equals(event.getParent())) {
                        new ActivityUnit(fu, event.getName(), event.getCode());
                    }
                }
            }
        }
        return handled(event);
    }

    EvtSiteActivityUnitDeleted handle(EvtSiteActivityUnitDeleted event) {
        for (Sector sector : sectors) {
            for (Service service : sector.getServices()) {
                for (FunctionalUnit fu : service.getUnits()) {
                    for (ActivityUnit au : fu.getUnits()) {
                        if (au.getCode().equals(event.getCode())) {
                            fu.remove(au);
                            break;
                        }
                    }
                }
            }
        }
        return handled(event);
    }

    EvtSiteRegistered handle(EvtSiteRegistered event) {
        apply(event);
        LocationQ sectorQ = event.getDefaultSector();
        Sector sector = new Sector(sectorQ.getSectorType(), sectorQ.getName(), sectorQ.getCode());
        sectors.add(sector);
        return handled(event);
    }

    EvtSiteMainInfoUpdated handle(EvtSiteMainInfoUpdated event) {
        return apply(event);
    }

    EvtSiteAdditionalInfoUpdated handle(EvtSiteAdditionalInfoUpdated event) {
        return apply(event);
    }

    EvtSiteRoomAdded handle(EvtSiteRoomAdded event) {
        rooms.add(event.getRoomId());
        return handled(event);
    }

    EvtSiteRoomRemoved handle(EvtSiteRoomRemoved event) {
        rooms.remove(event.getRoomId());
        return handled(event);
    }

    EvtSiteDeleted handle(EvtSiteDeleted event) {
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
