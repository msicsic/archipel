package com.tentelemed.archipel.site.domain.model;

import com.tentelemed.archipel.core.domain.model.BaseVO;

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
public class FunctionalUnit extends BaseVO implements Location {
    @NotNull String name;
    @NotNull String code;
    List<ActivityUnit> units = new ArrayList<>();
    @NotNull Service parent;

    private FunctionalUnit() {
    }

    public FunctionalUnit(Service parent, String name, String code) {
        this.parent = parent;
        this.name = name;
        this.code = code;
        this.parent = parent;
        this.parent.addFunctionalUnit(this);
        validate();
    }

    public void addActivityUnit(ActivityUnit unit) {
        units.add(unit);
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isMedical() {
        return parent.isMedical();
    }

    public List<ActivityUnit> getUnits() {
        return Collections.unmodifiableList(units);
    }

    public List<String> getLocationStrings() {
        List<String> result = new ArrayList<>();
        if (getUnits().isEmpty()) {
            return Arrays.asList("FU:" + code);
        }
        for (ActivityUnit unit : getUnits()) {
            result.add("FU:" + code + "|" + "AU:" + unit.getCode());
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FunctionalUnit)) return false;

        FunctionalUnit that = (FunctionalUnit) o;

        if (!code.equals(that.code)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
