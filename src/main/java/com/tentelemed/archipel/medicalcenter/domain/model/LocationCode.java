package com.tentelemed.archipel.medicalcenter.domain.model;

import com.tentelemed.archipel.core.domain.model.BaseVO;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 15:51
 */
public class LocationCode extends BaseVO {
    @NotNull String code;

    LocationCode() {
    }

    public LocationCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
