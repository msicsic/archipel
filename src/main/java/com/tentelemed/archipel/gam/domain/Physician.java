package com.tentelemed.archipel.gam.domain;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.gam.application.event.PhysicianInfoUpdated;
import com.tentelemed.archipel.gam.application.event.PhysicianRegistered;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/11/13
 * Time: 17:01
 */
public class Physician extends BaseAggregateRoot<PhysicianId> {

    @NotNull String firstName;
    @NotNull String lastName;
    @NotNull Specialty specialty;
    Address address;
    PhoneNumber phone;

    @Override
    protected Class<PhysicianId> getIdClass() {
        return PhysicianId.class;
    }

    // COMMANDS

    public List<DomainEvent> register(String firstName, String lastName, Specialty specialty) {
        validate("firstName", firstName);
        validate("lastName", lastName);
        validate("specialty", specialty);
        return list(new PhysicianRegistered(getEntityId(), firstName, lastName, specialty));
    }

    public List<DomainEvent> updateInfos(String firstName, String lastName, Address address, Specialty specialty, PhoneNumber phone) {
        validate("firstName", firstName);
        validate("lastName", lastName);
        validate("address", address);
        validate("specialty", specialty);
        validate("phone", phone);
        return list(new PhysicianInfoUpdated(getEntityId(), firstName, lastName, address, specialty, phone));
    }

    // EVENTS

    public void handle(PhysicianRegistered event) {
        this.firstName = event.firstName;
        this.lastName = event.lastName;
        this.specialty = event.specialty;
    }

    public void handle(PhysicianInfoUpdated evt) {
        this.firstName = evt.firstName;
        this.lastName = evt.lastName;
        this.address = evt.address;
        this.specialty = evt.specialty;
        this.phone = evt.phone;
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
