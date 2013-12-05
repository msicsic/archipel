package com.tentelemed.archipel.core.domain.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 14:30
 */
public class Country extends BaseVO {

    @NotNull @Size(min = 3, max = 3) private String isoCode;

    Country() {}

    public Country(String isoCode) {
        this.isoCode = validate("isoCode", isoCode);
    }

    public String getIsoCode() {
        return isoCode;
    }
}
