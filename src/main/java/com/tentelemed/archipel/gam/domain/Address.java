package com.tentelemed.archipel.gam.domain;

import com.tentelemed.archipel.core.domain.model.Country;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/11/13
 * Time: 16:33
 */
public class Address {
    String street;
    String town;
    String postalCode;
    Country country;

    public Address(String street, String postalCode, String town, Country country) {
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
