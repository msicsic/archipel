package com.tentelemed.archipel.site.domain.model;

import com.tentelemed.archipel.core.domain.model.BaseEntity;
import com.tentelemed.archipel.site.domain.pub.Location;
import com.tentelemed.archipel.site.domain.pub.LocationPath;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 13:26
 */
public class Service extends BaseEntity implements Location {
    @NotNull String name;
    @NotNull @Size(min=3, max=3) String code;
    @NotNull Sector parent;
    Set<FunctionalUnit> units = new HashSet<>();

    private Service() {
    }

    public Service(Sector parent, String name, String code) {
        this.name = name;
        this.code = code;
        this.parent = parent;
        this.parent.addService(this);
        validate();
    }

    public void addFunctionalUnit(FunctionalUnit unit) {
        units.add(unit);
    }

    public void remove(FunctionalUnit fu) {
        units.remove(fu);
    }

    @Override
    public boolean isMedical() {
        return parent.isMedical();
    }

    @Override
    public Set<? extends Location> getChildren() {
        return units;
    }

    @Override
    public String getPrefix() {
        return "SRV";
    }

    @Override
    public String getCode() {
        return code;
    }

    public Location getParent() {
        return parent;
    }

    @Override
    public String getName() {
        return name;
    }

    public Set<FunctionalUnit> getUnits() {
        return Collections.unmodifiableSet(units);
    }

    public List<LocationPath> getLocationPaths() {
        List<LocationPath> result = new ArrayList<>();
        result.add(getPath());
        for (FunctionalUnit unit : getUnits()) {
            result.addAll(unit.getLocationPaths());
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service)) return false;

        Service service = (Service) o;

        if (!code.equals(service.code)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public LocationPath getPath() {
        return new LocationPath(this);
    }
}
