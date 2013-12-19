package com.tentelemed.archipel.site.domain.model;

import com.tentelemed.archipel.core.domain.model.BaseEntity;
import com.tentelemed.archipel.core.domain.model.BaseVO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 11:57
 */
public class Sector extends BaseEntity<SectorId> implements Location {
    public static enum Type {
        TECH, MEDTECH, ADMIN, MED
    }

    @NotNull @Size(min = 3) String name;
    @NotNull @Size(min = 3) String code;
    @NotNull Type type;
    List<Service> services = new ArrayList<>();

    Sector() {
    }

    public Sector(SectorId id, Type type, String name, String code) {
        this.id = id.getId();
        this.type = type;
        this.name = name;
        this.code = code;
        validate();
    }

    public void addService(Service service) {
        services.add(service);
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

    public List<Service> getServices() {
        return Collections.unmodifiableList(services);
    }

    public Type getType() {
        return type;
    }

    @Override
    protected Class<SectorId> getIdClass() {
        return SectorId.class;
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
