package com.tentelemed.archipel.gam.domain;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.gam.application.event.PhysicianInfoUpdated;

import java.util.List;

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

    // COMMANDS

    public List<DomainEvent> updateInfos(String firstName, String lastName, Address address, Specialty specialty, PhoneNumber phone) {
        return list(new PhysicianInfoUpdated(firstName, lastName, address, specialty, phone));
    }

    // EVENTS
    public void handle(PhysicianInfoUpdated evt) {
        this.firstName = evt.getFirstName();
        this.lastName = evt.getLastName();
        this.address = evt.getAddress();
        this.specialty = evt.getSpecialty();
        this.phone = evt.getPhone();
    }

    // GETTERS

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
