package com.tentelemed.archipel.medicalcenter.domain.model;

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
public class Sector extends BaseVO implements Location {
    public static enum Type {
        TECH, MEDTECH, ADMIN, MED
    }

    @NotNull @Size(min=3) String name;
    @NotNull @Size(min=3) String code;
    @NotNull Type type;
    List<Service> services = new ArrayList<>();

    public Sector(Type type, String name, String code, List<Service> services) {
        this.type = type;
        this.name = name;
        this.code = code;
        this.services = services==null ? new ArrayList<Service>() : services;
        validate();
    }

    public Collection<LocationCode> getLocationCodes() {
        List<LocationCode> result = new ArrayList<>();
        for (Service service : services) {
            List<String> scodes = service.getLocationStrings();
            for (String scode : scodes) {
                result.add(new LocationCode("SEC:"+code+"|"+scode));
            }
        }
        return result;
    }

    public String getName() {
        return name;
    }

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
}
