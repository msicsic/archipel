package com.tentelemed.archipel.core.domain.model;

import com.tentelemed.archipel.core.domain.model.BaseVO;
import com.tentelemed.archipel.core.domain.model.Country;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/11/13
 * Time: 16:33
 */
public class Address extends BaseVO {
    private String street;
    private String town;
    private String postalCode;
    private Country country;

    // constructeur privé nécessaire pour des raisons techniques (reconstruire l'objet a partir d'un Memento)
    Address() {}

    public Address(String street, String postalCode, String town, Country country) {
        this.street = validate("street", street);
        this.postalCode = validate("postalCode", postalCode);
        this.town = validate("town", town);
        this.country = validate("country", country);
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
