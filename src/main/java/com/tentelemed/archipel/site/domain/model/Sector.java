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
 * Time: 11:57
 */
public class Sector extends BaseEntity implements Location {

    public static enum Type {
        TECH, MEDTECH, ADMIN, MED
    }

    @NotNull @Size(min = 3) String name;
    @NotNull @Size(min = 3, max=3) String code;
    @NotNull Type type;
    Set<Service> services = new HashSet<>();

    Sector() {
    }

    public Sector(Type type, String name, String code) {
        this.type = type;
        this.name = name;
        this.code = code;
        validate();
    }

    public void addService(Service service) {
        services.add(service);
    }

    public void remove(Service service) {
        services.remove(service);
    }

    public Collection<LocationPath> getLocationPaths() {
        List<LocationPath> result = new ArrayList<>();
        result.add(getPath());
        for (Service service : services) {
            result.addAll(service.getLocationPaths());
        }
        return result;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getPrefix() {
        return "SEC";
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public LocationPath getPath() {
        return new LocationPath(this);
    }

    public boolean isMedical() {
        return this.type == Type.MED;
    }

    @Override
    public Set<? extends Location> getChildren() {
        return services;
    }

    @Override
    public Location getParent() {
        return null;
    }

    public Set<Service> getServices() {
        return Collections.unmodifiableSet(services);
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sector)) return false;

        Sector sector = (Sector) o;

        if (!code.equals(sector.code)) return false;
        if (type != sector.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
