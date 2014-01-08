package com.tentelemed.archipel.site.domain.model;

import com.tentelemed.archipel.core.domain.model.BaseEntity;

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

    public Collection<String> getLocationCodes() {
        List<String> result = new ArrayList<>();
        for (Service service : services) {
            List<String> scodes = service.getLocationStrings();
            for (String scode : scodes) {
                result.add(new String("SEC:" + code + "|" + scode));
            }
        }
        return result;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getCode() {
        return code;
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
