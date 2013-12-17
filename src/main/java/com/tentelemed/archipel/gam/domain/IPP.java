package com.tentelemed.archipel.gam.domain;

import com.tentelemed.archipel.core.domain.model.BaseVO;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/11/13
 * Time: 16:12
 */
public class IPP extends BaseVO {
    private String value;

    IPP() {
    }

    public IPP(String value) {
        assertThat(value, notNullValue());
        // TODO : verif format
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
