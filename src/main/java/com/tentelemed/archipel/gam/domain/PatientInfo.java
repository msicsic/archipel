package com.tentelemed.archipel.gam.domain;

import com.tentelemed.archipel.core.domain.model.ValueObject;
import javax.persistence.Embeddable;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 14:26
 */
@ValueObject
@Embeddable
public class PatientInfo {

    Address address;
    PhoneNumber mainPhone;
    PhoneNumber mobilePhone;
    Email email;

    public Address getAddress() {
        return address;
    }

    public PhoneNumber getMainPhone() {
        return mainPhone;
    }

    public PhoneNumber getMobilePhone() {
        return mobilePhone;
    }

    public Email getEmail() {
        return email;
    }

    public PatientInfo(Address address, Email email, PhoneNumber mainPhone, PhoneNumber mobilePhone) {
        this.address = address;
        this.email = email;
        this.mainPhone = mainPhone;
        this.mobilePhone = mobilePhone;
    }
}
