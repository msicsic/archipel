package com.tentelemed.archipel.core.domain.model;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 05/12/13
 * Time: 14:43
 */
public class TestCountry extends BaseVO {
    private String ISO;

    private TestCountry() {}

    public TestCountry(String ISO) {
        this.ISO = ISO;
    }

    public String getISO() {
        return ISO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestCountry)) return false;

        TestCountry that = (TestCountry) o;

        if (ISO != null ? !ISO.equals(that.ISO) : that.ISO != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return ISO != null ? ISO.hashCode() : 0;
    }
}
