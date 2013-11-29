package com.tentelemed.archipel.gam.domain;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/11/13
 * Time: 18:02
 */
public class Specialty {
    String name;

    public Specialty(String name) {
        assertThat(name, notNullValue());
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
