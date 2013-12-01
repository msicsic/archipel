package com.tentelemed.archipel.gam.domain;

import com.tentelemed.archipel.core.domain.model.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/11/13
 * Time: 16:36
 */
public class PhoneNumber extends BaseVO {
    private final String value;

    public PhoneNumber(String value) {
        // TODO : verifier le format
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
