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
public class Service extends BaseVO implements Location {
    @NotNull String name;
    @NotNull String code;
    List<FunctionalUnit> units = new ArrayList<>();

    private Service() {}

    public Service(String name, String code, List<FunctionalUnit> units) {
        this.name = name;
        this.code = code;
        this.units = units==null ? new ArrayList<FunctionalUnit>() : units;
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

    public List<FunctionalUnit> getUnits() {
        return Collections.unmodifiableList(units);
    }

    public List<String> getLocationStrings() {
        List<String> result = new ArrayList<>();
        if (getUnits().isEmpty()) {
            return Arrays.asList("SRV:"+code);
        }
        for (FunctionalUnit unit : getUnits()) {
            for (String ls : unit.getLocationStrings()) {
                result.add("SRV:"+code+"|"+ls);
            }
        }
        return result;
    }
}
