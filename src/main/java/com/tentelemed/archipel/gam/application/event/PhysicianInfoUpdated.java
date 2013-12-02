package com.tentelemed.archipel.gam.application.event;

import com.tentelemed.archipel.core.application.event.AbstractDomainEvent;
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
    public final String firstName;
    public final String lastName;
    public final Address address;
    public final Specialty specialty;
    public final PhoneNumber phone;

    public PhysicianInfoUpdated(PhysicianId id, String firstName, String lastName, Address address, Specialty specialty, PhoneNumber phone) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.specialty = specialty;
        this.phone = phone;
    }
}
