package com.tentelemed.archipel.site.domain.model;

import com.tentelemed.archipel.core.domain.model.BaseEntity;

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
