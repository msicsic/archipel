package com.tentelemed.archipel.site.infrastructure.model;

import com.tentelemed.archipel.core.infrastructure.model.BaseEntityQ;
import com.tentelemed.archipel.site.domain.event.SiteRegistered;
import com.tentelemed.archipel.site.domain.model.SiteId;
import com.tentelemed.archipel.site.domain.model.SiteInfo;
import com.tentelemed.archipel.site.domain.model.SiteType;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

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
    @NotNull @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) Set<LocationQ> sectors = new HashSet<>();

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

//    public void handleEvent(SiteRegistered event) {
//        this.ident = event.getIdent();
//        this.name = event.getName();
//        this.type = event.getType();
//        this.sectors = event.getSectors();
//    }
}