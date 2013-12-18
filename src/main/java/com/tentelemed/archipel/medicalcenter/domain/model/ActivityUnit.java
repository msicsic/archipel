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
    @NotNull FunctionalUnit parent;

    ActivityUnit() {
    }

    public ActivityUnit(FunctionalUnit parent, String name, String code) {
        this.name = name;
        this.code = code;
        this.parent = parent;
        this.parent.addActivityUnit(this);
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

    @Override
    public boolean isMedical() {
        return parent.isMedical();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityUnit)) return false;

        ActivityUnit that = (ActivityUnit) o;

        if (!code.equals(that.code)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
