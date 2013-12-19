package com.tentelemed.archipel.site.infrastructure.model;

import com.tentelemed.archipel.site.domain.model.Sector;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 12:07
 */
@Entity
public class LocationQ {
    public static enum Type {
        SECTOR, SERVICE, FU, AU
    }

    @Id String code;
    @NotNull Type type;
    @NotNull Sector.Type sectorType = Sector.Type.MED;
    @NotNull String name;
    @NotNull @OneToMany(mappedBy = "parent") List<LocationQ> children = new ArrayList<>();
    @NotNull @ManyToOne LocationQ parent;

    public LocationQ() {
    }

    public LocationQ(Type type, String name, String code) {
        this.type = type;
        this.name = name;
        this.code = code;
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

    public List<LocationQ> getChildren() {
        return children;
    }

    public void setChildren(List<LocationQ> children) {
        this.children = children;
    }

    public LocationQ getParent() {
        return parent;
    }

    public void setParent(LocationQ parent) {
        this.parent = parent;
    }

    public Sector.Type getSectorType() {
        return sectorType;
    }

    public void setSectorType(Sector.Type sectorType) {
        this.sectorType = sectorType;
    }
}
