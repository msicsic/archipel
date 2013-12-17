package com.tentelemed.archipel.gam.domain;

import com.tentelemed.archipel.core.domain.model.BaseVO;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/11/13
 * Time: 18:02
 */
public class Specialty extends BaseVO {
    @NotNull private String name;

    Specialty() {
    }

    public Specialty(String name) {
        //assertThat(name, notNullValue());
        validate("name", name);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
