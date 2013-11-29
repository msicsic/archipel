package com.tentelemed.archipel.gam.domain;

import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/11/13
 * Time: 17:01
 */
public class Physician extends BaseAggregateRoot<PhysicianId> {

    String firstName;
    String lastName;
    Address address;
    Specialty specialty;
    PhoneNumber phone;

    @Override
    protected Class<PhysicianId> getIdClass() {
        return PhysicianId.class;
    }

    public Physician(String firstName, String lastName, Specialty specialty) {
        assertThat(firstName, notNullValue());
        assertThat(lastName, notNullValue());
        assertThat(specialty, notNullValue());

        this.firstName = firstName;
        this.lastName = lastName;
        this.specialty = specialty;
    }

    public void updateInfos(String firstName, String lastName, Address address, Specialty specialty, PhoneNumber phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.specialty = specialty;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress() {
        return address;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public PhoneNumber getPhone() {
        return phone;
    }
}
