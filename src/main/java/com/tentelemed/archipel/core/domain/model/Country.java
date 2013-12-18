package com.tentelemed.archipel.core.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 14:30
 */

@Entity
public class Country extends BaseVO {

    @Id @NotNull @Size(min = 3, max = 3) private String isoCode;
    @NotNull String name;

    Country() {
    }

    public Country(String isoCode, String name) {
        this.isoCode = validate("isoCode", isoCode);
        this.name = name;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country)) return false;

        Country country = (Country) o;

        if (!isoCode.equals(country.isoCode)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return isoCode.hashCode();
    }
}
