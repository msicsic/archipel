package com.tentelemed.archipel.core.domain.model;

import com.tentelemed.archipel.core.domain.model.BaseVO;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/11/13
 * Time: 16:36
 */
public class PhoneNumber extends BaseVO {
    // TODO : verifier le format
    @NotNull private String value;

    PhoneNumber() {}

    public PhoneNumber(String value) {
        this.value = validate("value", value);
    }

    public String getValue() {
        return value;
    }
}
