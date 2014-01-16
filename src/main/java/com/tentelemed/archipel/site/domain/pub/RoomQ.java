package com.tentelemed.archipel.site.domain.pub;

import com.tentelemed.archipel.core.domain.pub.BaseEntityQ;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name="Beds",
            joinColumns=@JoinColumn(name="OWNER_ID")
    )
    @Column(name="BED_NAME")
    List<Bed> beds = new ArrayList<>();
//    @NotNull @Valid @OneToMany(mappedBy = "room") Set<Bed> beds = new HashSet<>();
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

    public List<Bed> getBeds() {
        return beds;
    }

    public void setBeds(List<Bed> beds) {
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
