package com.tentelemed.archipel.gam.domain;

import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.domain.model.Address;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.PhoneNumber;
import com.tentelemed.archipel.gam.application.event.PhysicianInfoUpdated;
import com.tentelemed.archipel.gam.application.event.PhysicianRegistered;

import javax.validation.constraints.NotNull;


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

    public CmdRes register(String firstName, String lastName, Specialty specialty) {
        validate("firstName", firstName);
        validate("lastName", lastName);
        validate("specialty", specialty);
        return _result(handle(new PhysicianRegistered(getEntityId(), firstName, lastName, specialty)));
    }

    public CmdRes updateInfos(String firstName, String lastName, Address address, Specialty specialty, PhoneNumber phone) {
        validate("firstName", firstName);
        validate("lastName", lastName);
        validate("address", address);
        validate("specialty", specialty);
        validate("phone", phone);
        return _result(handle(new PhysicianInfoUpdated(getEntityId(), firstName, lastName, address, specialty, phone)));
    }

    // EVENTS

    public PhysicianRegistered handle(PhysicianRegistered event) {
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.specialty = event.getSpecialty();
        return handled(event);
    }

    public PhysicianInfoUpdated handle(PhysicianInfoUpdated event) {
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.address = event.getAddress();
        this.specialty = event.getSpecialty();
        this.phone = event.getPhone();
        return handled(event);
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
