package com.tentelemed.archipel.medicalcenter.domain.model;

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

    private FunctionalUnit() {
    }

    public FunctionalUnit(String name, String code, List<ActivityUnit> units) {
        this.name = name;
        this.code = code;
        this.units = units;
        validate();
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
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
}
