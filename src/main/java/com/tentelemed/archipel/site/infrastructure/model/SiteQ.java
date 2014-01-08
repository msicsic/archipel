package com.tentelemed.archipel.site.infrastructure.model;

import com.tentelemed.archipel.core.infrastructure.model.BaseEntityQ;
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

    /*void applyEvent(EvtSiteRoomAdded event) {
        // TODO

    }

    void applyEvent(EvtSiteRoomRemoved event) {
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

    public List<Sector.Type> getRemainingSectorTypes() {
        List<Sector.Type> types = new ArrayList<>(Arrays.asList(Sector.Type.values()));
        for (LocationQ sector : sectors) {
            types.remove(sector.getSectorType());
        }
        return types;
    }

    public LocationQ findLocation(String parentCode) {
        return _findLocation(sectors, parentCode);
    }

    private LocationQ _findLocation(Set<LocationQ> locations, String code) {
        for (LocationQ loc : locations) {
            if (loc.getCode().equals(code)) {
                return loc;
            }
        }
        for (LocationQ loc : locations) {
            LocationQ found = _findLocation(loc.getChildren(), code);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
}