package com.tentelemed.archipel.site.infrastructure.model;

import com.tentelemed.archipel.core.infrastructure.model.BaseEntityQ;
import com.tentelemed.archipel.site.domain.pub.Bed;
import com.tentelemed.archipel.site.domain.pub.RoomId;
import com.tentelemed.archipel.site.domain.pub.SiteId;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 12:07
 */
@Entity
public class RoomQ extends BaseEntityQ<RoomId> {
    @NotNull @Size(min = 2, max = 50) String name;
    boolean medical;
    @NotNull @Valid @ElementCollection Set<Bed> beds = new HashSet<>();
    @NotNull String locationPath;
    @NotNull SiteId siteId;

    @Override
    protected Class<RoomId> getIdClass() {
        return RoomId.class;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMedical() {
        return medical;
    }

    public void setMedical(boolean medical) {
        this.medical = medical;
    }

    public Set<Bed> getBeds() {
        return beds;
    }

    public void setBeds(Set<Bed> beds) {
        this.beds = beds;
    }

    public String getLocationPath() {
        return locationPath;
    }

    public void setLocationPath(String locationPath) {
        this.locationPath = locationPath;
    }

    public int getNbBeds() {
        return beds.size();
    }

    public SiteId getSiteId() {
        return siteId;
    }

    public void setSiteId(SiteId siteId) {
        this.siteId = siteId;
    }
}
