package com.tentelemed.archipel.medicalcenter.domain.model;

import com.tentelemed.archipel.core.domain.model.BaseVO;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 13:26
 */
public class ActivityUnit extends BaseVO implements Location {
    @NotNull String name;
    @NotNull String code;

    ActivityUnit() {
    }

    public ActivityUnit(String name, String code) {
        this.name = name;
        this.code = code;
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
}
