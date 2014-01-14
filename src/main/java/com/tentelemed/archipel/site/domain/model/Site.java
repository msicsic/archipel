package com.tentelemed.archipel.site.domain.model;

import com.tentelemed.archipel.core.application.command.CmdRes;
import com.tentelemed.archipel.core.domain.model.Address;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.DomainException;
import com.tentelemed.archipel.site.application.command.*;
import com.tentelemed.archipel.site.domain.pub.*;
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

    @Override
    public CmdRes execute(CmdSiteCreateFunctionalUnit cmd) {
        cmd.code = cmd.code.toUpperCase();
        if (getLocationsCodes().contains(cmd.code)) throw new DomainException("This code is allready used !");

        Location found = findLocation(cmd.parent);
        if (found == null) throw new DomainException("Service not found");
        if (! (found instanceof Service)) throw new DomainException("FunctionalUnit can only be added to a Service");
        return _result(handle(new EvtSiteFunctionalUnitAdded(getEntityId(), cmd.parent, cmd.code, cmd.name)));
    }

    @Override
    public CmdRes execute(CmdSiteCreateActivityUnit cmd) {
        cmd.code = cmd.code.toUpperCase();
        if (getLocationsCodes().contains(cmd.code)) throw new DomainException("This code is allready used !");

        Location found = findLocation(cmd.parent);
        if (found == null) throw new DomainException("FunctionalUnit not found");
        if (! (found instanceof FunctionalUnit)) throw new DomainException("ActivityUnit must be added to a FunctionalUnit");
        return _result(handle(new EvtSiteActivityUnitAdded(getEntityId(), cmd.parent, cmd.code, cmd.name)));
    }

    @Override
    public CmdRes execute(CmdSiteCreateService cmd) {
        cmd.code = cmd.code.toUpperCase();
        if (getLocationsCodes().contains(cmd.code)) throw new DomainException("This code is allready used !");

        Location found = findLocation(cmd.sectorCode);
        if (found == null) throw new DomainException("Sector not found");
        if (! (found instanceof Sector)) throw new DomainException("Service must be added to Sector");
        return _result(handle(new EvtSiteServiceAdded(getEntityId(), cmd.sectorCode, cmd.code, cmd.name)));
    }

    @Override
    public CmdRes execute(CmdSiteCreateSector cmd) {
        cmd.code = cmd.code.toUpperCase();
        // le type ne doit pas deja etre présent
        if (getLocationsCodes().contains(cmd.code)) throw new DomainException("This code is allready used !");

        for (Sector s : sectors) {
            if (s.getType() == cmd.type) {
                throw new DomainException("This type of Sector is already present");
            }
        }
        return _result(handle(new EvtSiteSectorAdded(getEntityId(), cmd.type, cmd.code, cmd.name)));
    }

    @Override
    public CmdRes execute(CmdSiteDeleteSector cmd) {
        Location location = findLocation(cmd.code);
        if (location == null) throw new DomainException("this SectorCode is not present in this Site");
        if (! (location instanceof Sector)) throw new DomainException("Code to remove is not a Sector");
        Sector sector = (Sector) location;
        if (sector.getType().equals(Sector.Type.MED)) {
            throw new DomainException("Default sector (MED) cannot be deleted");
        }
        return _result(handle(new EvtSiteSectorDeleted(getEntityId(), cmd.code)));
    }

    @Override
    public CmdRes execute(CmdSiteDeleteService cmd) {
        Location location = findLocation(cmd.code);
        if (location == null) {
            throw new DomainException("this Service is not present in this Site");
        }
        return _result(handle(new EvtSiteServiceDeleted(getEntityId(), cmd.code)));
    }

    @Override
    public CmdRes execute(CmdSiteDeleteFunctionalUnit cmd) {
        Location location = findLocation(cmd.code);
        if (location == null) {
            throw new DomainException("this FunctionalUnitCode is not present in this Site");
        }
        return _result(handle(new EvtSiteFunctionalUnitDeleted(getEntityId(), cmd.code)));
    }

    @Override
    public CmdRes execute(CmdSiteDeleteActivityUnit cmd) {
        Location location = findLocation(cmd.code);
        if (location == null) {
            throw new DomainException("this ActivityUnitCode is not present in this Site");
        }
        return _result(handle(new EvtSiteActivityUnitDeleted(getEntityId(), cmd.code)));
    }

    // methodes utilitaires

    /**
     * Liste des identifiants de Location
     * @return
     */
    public Set<String> getLocationsCodes() {
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

    public Location getLocationFromPath(LocationPath locationPath) {
        int pos = locationPath.toString().lastIndexOf(":");
        String code = locationPath.toString().substring(pos+1);
        return findLocation(code);
    }

    public List<LocationPath> getLocationPaths() {
        List<LocationPath> result = new ArrayList<>();
        for (Sector sector : sectors) {
            result.addAll(sector.getLocationPaths());
        }
        return result;
    }

    public Location findLocation(String locationPath) {
        Set<Location> locations = new HashSet<>();
        locations.addAll(sectors);
        return _findLocation(locations, locationPath);
    }

    private Location _findLocation(Set<? extends Location> locations, String code) {
        for (Location location : locations) {
            if (location.getCode().equals(code)) {
                return location;
            }
        }
        for (Location location : locations) {
            Location found = _findLocation(location.getChildren(), code);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    private void removeLocation(String locationCode) {
        Location location = findLocation(locationCode);
        if (location.getParent() == null) {
            sectors.remove(location);
        } else {
            location.getParent().getChildren().remove(location);
        }
    }

    // EVENTS

    EvtSiteSectorAdded handle(EvtSiteSectorAdded event) {
        Sector sector = new Sector(event.getSectorType(), event.getSectorName(), event.getSectorCode());
        sectors.add(sector);
        return handled(event);
    }

    EvtSiteSectorDeleted handle(EvtSiteSectorDeleted event) {
        removeLocation(event.getSectorCode());
        return handled(event);
    }

    EvtSiteServiceAdded handle(EvtSiteServiceAdded event) {
        Location parent = findLocation(event.getParent());
        new Service((Sector) parent, event.getName(), event.getCode());
        return handled(event);
    }

    EvtSiteServiceDeleted handle(EvtSiteServiceDeleted event) {
        removeLocation(event.getServiceCode());
        return handled(event);
    }

    EvtSiteFunctionalUnitAdded handle(EvtSiteFunctionalUnitAdded event) {
        Location parent = findLocation(event.getParent());
        new FunctionalUnit((Service) parent, event.getName(), event.getCode());
        return handled(event);
    }

    EvtSiteFunctionalUnitDeleted handle(EvtSiteFunctionalUnitDeleted event) {
        removeLocation(event.getCode());
        return handled(event);
    }

    EvtSiteActivityUnitAdded handle(EvtSiteActivityUnitAdded event) {
        Location parent = findLocation(event.getParent());
        new ActivityUnit((FunctionalUnit) parent, event.getName(), event.getCode());
        return handled(event);
    }

    EvtSiteActivityUnitDeleted handle(EvtSiteActivityUnitDeleted event) {
        removeLocation(event.getCode());
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
        return handled(event);
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
