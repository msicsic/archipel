package com.tentelemed.archipel.site.infrastructure.model;

import com.tentelemed.archipel.core.infrastructure.model.BaseEntityQ;
import com.tentelemed.archipel.site.domain.event.*;
import com.tentelemed.archipel.site.domain.model.Sector;
import com.tentelemed.archipel.site.domain.model.SiteId;
import com.tentelemed.archipel.site.domain.model.SiteInfo;
import com.tentelemed.archipel.site.domain.model.SiteType;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 12/12/13
 * Time: 14:25
 */
@Entity
public class SiteQ extends BaseEntityQ<SiteId> {
    @NotNull @Size(min = 3) String name;
    @NotNull @Size(min = 3) String ident;
    @NotNull SiteType type;
    @Valid @Embedded SiteInfo info;
    @NotNull @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER) Set<LocationQ> sectors = new HashSet<>();

    @Override
    protected Class<SiteId> getIdClass() {
        return SiteId.class;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public SiteType getType() {
        return type;
    }

    public void setType(SiteType type) {
        this.type = type;
    }

    public SiteInfo getInfo() {
        return info;
    }

    public void setInfo(SiteInfo info) {
        this.info = info;
    }

    public Set<LocationQ> getSectors() {
        return sectors;
    }

    public void setSectors(Set<LocationQ> sectors) {
        this.sectors = sectors;
    }

    void applyEvent(SiteRegistered event) {
        this.id = event.getId().getId();
        this.ident = event.getIdent();
        this.name = event.getName();
        this.type = event.getType();
        this.sectors.add(event.getDefaultSector());
    }

    void applyEvent(SiteAdditionalInfoUpdated event) {
        this.info = event.getInfo();
    }

    void applyEvent(SiteDeleted event) {
        // ras
    }

    void applyEvent(SiteMainInfoUpdated event) {
        this.name = event.getName();
        this.type = event.getType();
        this.ident = event.getIdent();
    }

    /*void applyEvent(SiteRoomAdded event) {
        // TODO

    }

    void applyEvent(SiteRoomRemoved event) {
        // TODO
    }*/

    boolean addLocation(int currentLevel, int goalLevel, LocationQ parent, Set<LocationQ> locations, LocationQ location) {
        if (currentLevel == goalLevel) {
            if (parent == null) {
                if (location.getParent() != null) {
                    throw new RuntimeException("ERROR");
                }
                // racine = sector
                locations.add(location);
            } else {
                if (parent.getCode().equals(location.getParent().getCode())) {
                    parent.addChild(location);
                    return true;
                }
            }
        } else {
            for (LocationQ loc : locations) {
                boolean added = addLocation(currentLevel + 1, goalLevel, loc, loc.getChildren(), location);
                if (added) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean removeLocation(int currentLevel, int goalLevel, Set<LocationQ> locations, String locationCode) {
        if (currentLevel == goalLevel) {
            for (LocationQ loc : locations) {
                if (loc.getCode().equals(locationCode)) {
                    locations.remove(loc);
                    return true;
                }
            }
        } else {
            for (LocationQ loc : locations) {
                boolean res = removeLocation(currentLevel + 1, goalLevel, loc.getChildren(), locationCode);
                if (res) {
                    return true;
                }
            }
        }
        return false;
    }

    void applyEvent(SiteServiceAdded event) {
        LocationQ service = new LocationQ(event.getId(), LocationQ.Type.SERVICE, event.getName(), event.getCode());
        addLocation(0, 1, null, sectors, service);
    }

    void applyEvent(SiteServiceDeleted event) {
        removeLocation(0, 1, sectors, event.getServiceCode());
    }

    void applyEvent(SiteSectorAdded event) {
        LocationQ sector = new LocationQ(event.getId(), LocationQ.Type.SECTOR, event.getSectorName(), event.getSectorCode());
        sector.setSectorType(event.getSectorType());
        addLocation(0, 0, null, sectors, sector);
    }

    void applyEvent(SiteSectorDeleted event) {
        removeLocation(0, 0, sectors, event.getSectorCode());
    }

    void applyEvent(SiteFunctionalUnitAdded event) {
        LocationQ location = new LocationQ(event.getId(), LocationQ.Type.FU, event.getName(), event.getCode());
        addLocation(0, 2, null, sectors, location);
    }

    void applyEvent(SiteFunctionalUnitDeleted event) {
        removeLocation(0, 2, sectors, event.getCode());
    }

    void applyEvent(SiteActivityUnitAdded event) {
        LocationQ location = new LocationQ(event.getId(), LocationQ.Type.FU, event.getName(), event.getCode());
        addLocation(0, 3, null, sectors, location);
    }

    void applyEvent(SiteActivityUnitDeleted event) {
        removeLocation(0, 3, sectors, event.getCode());
    }

    public List<Sector.Type> getRemainingSectorTypes() {
        List<Sector.Type> types = new ArrayList<>(Arrays.asList(Sector.Type.values()));
        for (LocationQ sector : sectors) {
            types.remove(sector.getSectorType());
        }
        return types;
    }
}