package com.tentelemed.archipel.gam.application.event;

import com.tentelemed.archipel.core.application.event.AbstractDomainEvent;
import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.gam.domain.Address;
import com.tentelemed.archipel.gam.domain.PhoneNumber;
import com.tentelemed.archipel.gam.domain.PhysicianId;
import com.tentelemed.archipel.gam.domain.Specialty;

/**
 * Created with IntelliJ IDEA.
 * User: mael
 * Date: 30/11/2013
 * Time: 17:07
 */

public class PhysicianInfoUpdated extends AbstractDomainEvent<PhysicianId> {
    String firstName;
    String lastName;
    Address address;
    Specialty specialty;
    PhoneNumber phone;


    public PhysicianInfoUpdated(String firstName, String lastName, Address address, Specialty specialty, PhoneNumber phone) {
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
