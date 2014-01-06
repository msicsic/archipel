package com.tentelemed.archipel.site.domain.model;

import com.tentelemed.archipel.core.domain.model.BaseEntity;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 13:26
 */
public class Service extends BaseEntity implements Location {
    @NotNull String name;
    @NotNull String code;
    @NotNull Sector parent;
    List<FunctionalUnit> units = new ArrayList<>();

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
    public String getCode() {
        return code;
    }

    public Sector getParent() {
        return parent;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<FunctionalUnit> getUnits() {
        return Collections.unmodifiableList(units);
    }

    public List<String> getLocationStrings() {
        List<String> result = new ArrayList<>();
        if (getUnits().isEmpty()) {
            return Arrays.asList("SRV:" + code);
        }
        for (FunctionalUnit unit : getUnits()) {
            for (String ls : unit.getLocationStrings()) {
                result.add("SRV:" + code + "|" + ls);
            }
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

}
