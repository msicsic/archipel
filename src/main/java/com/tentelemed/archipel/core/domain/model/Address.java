package com.tentelemed.archipel.core.domain.model;

import javax.persistence.Embeddable;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/11/13
 * Time: 16:33
 */
@Embeddable
public class Address extends BaseVO {
    private String street;
    private String town;
    private String postalCode;
    private String countryIso;

    // constructeur privé nécessaire pour des raisons techniques (reconstruire l'objet a partir d'un Memento)
    Address() {
    }

    public Address(String street, String postalCode, String town, String countryIso) {
        this.street = validate("street", street);
        this.postalCode = validate("postalCode", postalCode);
        this.town = validate("town", town);
        this.countryIso = validate("countryIso", countryIso);
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

    public String getCountryIso() {
        return countryIso;
    }
}
