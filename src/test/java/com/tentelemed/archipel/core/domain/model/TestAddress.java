package com.tentelemed.archipel.core.domain.model;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 05/12/13
 * Time: 14:43
 */
public class TestAddress extends BaseVO {
    private String street;
    private String town;
    private TestCountry country;

    private TestAddress() {}

    public TestAddress(String street, String town, TestCountry country) {
        this.street = street;
        this.town = town;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public String getTown() {
        return town;
    }

    public TestCountry getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAddress)) return false;

        TestAddress that = (TestAddress) o;

        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (town != null ? !town.equals(that.town) : that.town != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = street != null ? street.hashCode() : 0;
        result = 31 * result + (town != null ? town.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}
