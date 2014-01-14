package com.tentelemed.archipel.site.domain.pub;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 12:07
 */
@Entity
public class LocationQ implements Comparable<LocationQ> {

    public static enum Type {
        SECTOR, SERVICE, FU, AU
    }

    @Id @GeneratedValue Long id;
    @NotNull SiteId siteId;
    @NotNull String code;
    @NotNull Type type;
    @NotNull SectorType sectorType = SectorType.MED;
    @NotNull String name;
    @NotNull @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<LocationQ> children = new HashSet<>();
    @ManyToOne LocationQ parent;

    public LocationQ() {
    }

    public LocationQ(SiteId siteId, Type type, String name, String code, LocationQ parent) {
        this.siteId = siteId;
        this.type = type;
        this.name = name;
        this.code = code;
        this.parent = parent;
    }

    public void addChild(LocationQ loc) {
        getChildren().add(loc);
        loc.setParent(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SiteId getSiteId() {
        return siteId;
    }

    public void setSiteId(SiteId siteId) {
        this.siteId = siteId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<LocationQ> getChildren() {
        return children;
    }

    public void setChildren(Set<LocationQ> children) {
        this.children = children;
    }

    public LocationQ getParent() {
        return parent;
    }

    public void setParent(LocationQ parent) {
        this.parent = parent;
    }

    public SectorType getSectorType() {
        return sectorType;
    }

    public void setSectorType(SectorType sectorType) {
        this.sectorType = sectorType;
    }

    @Override
    public int compareTo(LocationQ o) {
        return getName().compareTo(o.getName());
    }
}
