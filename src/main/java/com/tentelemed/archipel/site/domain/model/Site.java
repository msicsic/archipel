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
public class Site extends BaseAggregateRoot<SiteId> implements CmdHandlerSite {

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
        LocationQ sector = new LocationQ(getEntityId(), LocationQ.Type.SECTOR, "Default", "MED");
        return _result(handle(new SiteRegistered(getEntityId(), cmd.type, cmd.name, cmd.ident, sector)));
    }

    @Override
    public CmdRes execute(CmdSiteUpdate cmd) {
        validate("type", cmd.type);
        validate("name", cmd.name);
        validate("ident", cmd.ident);
        return _result(handle(new SiteMainInfoUpdated(getEntityId(), cmd.type, cmd.name, cmd.ident)));
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
        return _result(handle(new SiteAdditionalInfoUpdated(getEntityId(), info)));
    }

    @Override
    public CmdRes execute(CmdSiteDelete cmd) {
        return _result(handle(new SiteDeleted(getEntityId())));
    }

    public CmdRes createFunctionalUnit(String serviceCode, String code, String name) {
        if (getSectorCodes().contains(code)) {
            throw new DomainException("This code is allready used !");
        }
        Service found = null;
        for (Sector sector : sectors) {
            for (Service service : sector.getServices()) {
                if (service.getCode().equals(serviceCode)) {
                    found = service;
                    break;
                }
            }
        }
        if (found == null) {
            throw new DomainException("Service not found");
        } else {
            return _result(handle(new SiteFunctionalUnitAdded(getEntityId(), serviceCode, code, name)));
        }
    }

    public CmdRes createActivityUnit(String parentCode, String code, String name) {
        if (getSectorCodes().contains(code)) {
            throw new DomainException("This code is allready used !");
        }
        Service found = null;
        for (Sector sector : sectors) {
            for (Service service : sector.getServices()) {
                for (FunctionalUnit unit : service.getUnits()) {
                    if (unit.getCode().equals(parentCode)) {
                        found = service;
                        break;
                    }
                }
            }
        }
        if (found == null) {
            throw new DomainException("FunctionalUnit not found");
        } else {
            return _result(handle(new SiteActivityUnitAdded(getEntityId(), parentCode, code, name)));
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
            return _result(handle(new SiteServiceAdded(getEntityId(), cmd.sectorCode, cmd.code, cmd.name)));
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
        return _result(handle(new SiteSectorAdded(getEntityId(), cmd.type, cmd.code, cmd.name)));
    }

    @Override
    public CmdRes execute(CmdSiteDeleteSector cmd) {
        for (Sector sector : sectors) {
            if (sector.getCode().equals(cmd.code)) {
                if (sector.getType().equals(Sector.Type.MED)) {
                    throw new DomainException("Default sector (MED) cannot be deleted");
                }
                return _result(handle(new SiteSectorDeleted(getEntityId(), cmd.code)));
            }
        }
        throw new DomainException("this SectorCode is not present in this Site");
    }

    @Override
    public CmdRes execute(CmdSiteDeleteService cmd) {
        for (Sector sector : sectors) {
            for (Service service : sector.getServices()) {
                if (service.getCode().equals(cmd.code)) {
                    return _result(handle(new SiteServiceDeleted(getEntityId(), cmd.code)));
                }
            }
        }
        throw new DomainException("this SectorCode is not present in this Site");
    }

    public CmdRes deleteFunctionalUnit(String code) {
        for (Sector sector : sectors) {
            for (Service service : sector.getServices()) {
                for (FunctionalUnit fu : service.getUnits()) {
                    if (fu.getCode().equals(code)) {
                        return _result(handle(new SiteFunctionalUnitDeleted(getEntityId(), code)));
                    }
                }
            }
        }
        throw new DomainException("this ServiceCode is not present in this Site");
    }

    public CmdRes deleteActivityUnit(String code) {
        for (Sector sector : sectors) {
            for (Service service : sector.getServices()) {
                for (FunctionalUnit fu : service.getUnits()) {
                    for (ActivityUnit au : fu.getUnits()) {
                        if (au.getCode().equals(code)) {
                            return _result(handle(new SiteActivityUnitDeleted(getEntityId(), code)));
                        }
                    }
                }
            }
        }
        throw new DomainException("this FunctionalUnitCode is not present in this Site");
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

    SiteSectorAdded handle(SiteSectorAdded event) {
        Sector sector = new Sector(event.getSectorType(), event.getSectorName(), event.getSectorCode());
        sectors.add(sector);
        return handled(event);
    }

    SiteSectorDeleted handle(SiteSectorDeleted event) {
        for (Sector sector : sectors) {
            if (sector.getCode().equals(event.getSectorCode())) {
                sectors.remove(sector);
                break;
            }
        }
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

    SiteServiceDeleted handle(SiteServiceDeleted event) {
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

    SiteFunctionalUnitAdded handle(SiteFunctionalUnitAdded event) {
        for (Sector sector : sectors) {
            for (Service service : sector.getServices()) {
                if (service.getCode().equals(event.getParent())) {
                    new FunctionalUnit(service, event.getName(), event.getCode());
                }
            }
        }
        return handled(event);
    }

    SiteFunctionalUnitDeleted handle(SiteFunctionalUnitDeleted event) {
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

    SiteActivityUnitAdded handle(SiteActivityUnitAdded event) {
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

    SiteActivityUnitDeleted handle(SiteActivityUnitDeleted event) {
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
