package com.tentelemed.archipel.gam.domain.pub;

import com.tentelemed.archipel.core.domain.pub.AbstractDomainEvent;

/**
 * Created with IntelliJ IDEA.
 * User: mael
 * Date: 30/11/2013
 * Time: 17:07
 */

public class PhysicianRegistered extends AbstractDomainEvent<PhysicianId> {

    private String firstName;
    private String lastName;
    private Specialty specialty;

    PhysicianRegistered() {
    }

    public PhysicianRegistered(PhysicianId id, String firstName, String lastName, Specialty specialty) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialty = specialty;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Specialty getSpecialty() {
        return specialty;
    }
}
