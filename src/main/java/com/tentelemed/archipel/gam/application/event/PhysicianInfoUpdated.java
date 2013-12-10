package com.tentelemed.archipel.gam.application.event;

import com.tentelemed.archipel.core.application.event.AbstractDomainEvent;
import com.tentelemed.archipel.core.domain.model.Address;
import com.tentelemed.archipel.core.domain.model.PhoneNumber;
import com.tentelemed.archipel.gam.domain.PhysicianId;
import com.tentelemed.archipel.gam.domain.Specialty;

/**
 * Created with IntelliJ IDEA.
 * User: mael
 * Date: 30/11/2013
 * Time: 17:07
 */

public class PhysicianInfoUpdated extends AbstractDomainEvent<PhysicianId> {
    private String firstName;
    private String lastName;
    private Address address;
    private Specialty specialty;
    private PhoneNumber phone;

    PhysicianInfoUpdated() {}

    public PhysicianInfoUpdated(PhysicianId id, String firstName, String lastName, Address address, Specialty specialty, PhoneNumber phone) {
        super(id);
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
