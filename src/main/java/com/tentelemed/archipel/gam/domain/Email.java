package com.tentelemed.archipel.gam.domain;

import com.tentelemed.archipel.core.domain.model.BaseVO;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/11/13
 * Time: 16:52
 */
public class Email extends BaseVO {
    private String value;

    Email() { }

    public Email(String value) {
        // TODO : vérif regexpr
        assertThat(value, notNullValue());
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
