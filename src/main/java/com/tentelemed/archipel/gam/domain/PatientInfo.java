package com.tentelemed.archipel.gam.domain;

import com.tentelemed.archipel.core.domain.model.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 14:26
 */
public class PatientInfo extends BaseVO {

    private Address address;
    private PhoneNumber mainPhone;
    private PhoneNumber mobilePhone;
    private Email email;

    PatientInfo() {}

    public PatientInfo(Address address, Email email, PhoneNumber mainPhone, PhoneNumber mobilePhone) {
        this.address = validate("address", address);
        this.email = validate("email", email);
        this.mainPhone = validate("mainPhone", mainPhone);
        this.mobilePhone = validate("mobilePhone", mobilePhone);
    }

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

}
