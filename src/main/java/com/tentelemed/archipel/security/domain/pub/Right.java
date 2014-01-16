package com.tentelemed.archipel.security.domain.pub;

import com.tentelemed.archipel.core.domain.model.BaseVO;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 01/12/13
 * Time: 02:01
 */
public class Right extends BaseVO {
    public final static Right RIGHT_A = new Right("admin", "*:*:*");
    public final static Right RIGHT_B = new Right("users", "sites:show,create:*");

    @NotNull private String name;
    @NotNull private String value;

    Right() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Right)) return false;

        Right right = (Right) o;

        if (!name.equals(right.name)) return false;
        if (!value.equals(right.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
