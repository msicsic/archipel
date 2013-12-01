package com.tentelemed.archipel.gam.domain;

import com.tentelemed.archipel.core.domain.model.BaseVO;
import com.tentelemed.archipel.core.domain.model.Country;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/11/13
 * Time: 16:33
 */
public class Address extends BaseVO {
    private final String street;
    private final String town;
    private final String postalCode;
    private final Country country;

    public Address(String street, String postalCode, String town, Country country) {
        validate("street", street);
        validate("postalCode", postalCode);
        validate("town", town);
        validate("country", country);
        this.street = street;
        this.postalCode = postalCode;
        this.town = town;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public String getTown() {
        return town;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Country getCountry() {
        return country;
    }
}
