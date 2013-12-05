package com.tentelemed.archipel.security.domain.model;

import com.tentelemed.archipel.core.domain.model.BaseVO;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 01/12/13
 * Time: 02:01
 */
public class Right extends BaseVO {
    public final static Right RIGHT_A = new Right("A", "*:*:*");
    public final static Right RIGHT_B = new Right("B", "*");

    @NotNull private String name;
    @NotNull private String value;

    Right() {}

    public Right(String name, String value) {
        this.name = validate("name", name);
        this.value = validate("value", value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
