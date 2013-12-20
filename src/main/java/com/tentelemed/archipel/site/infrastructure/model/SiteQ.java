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

    void applyEvent(SiteSectorDeleted event) {
        for (LocationQ sector : sectors) {
            if (sector.getCode().equals(event.getSectorCode())) {
                sectors.remove(sector);
                break;
            }
        }
    }

    void applyEvent(SiteServiceAdded event) {
        for (LocationQ sector : getSectors()) {
            if (event.getParent().equals(sector.getCode())) {
                LocationQ service = new LocationQ(LocationQ.Type.SERVICE, event.getName(), event.getCode());
                sector.addChild(service);
            }
        }
    }

    void applyEvent(SiteSectorAdded event) {
        LocationQ sector = new LocationQ(LocationQ.Type.SECTOR, event.getSectorName(), event.getSectorCode());
        sector.setSectorType(event.getSectorType());
        sectors.add(sector);
    }

    public List<Sector.Type> getRemainingSectorTypes() {
        List<Sector.Type> types = new ArrayList<>(Arrays.asList(Sector.Type.values()));
        for (LocationQ sector : sectors) {
            types.remove(sector.getSectorType());
        }
        return types;
    }
}