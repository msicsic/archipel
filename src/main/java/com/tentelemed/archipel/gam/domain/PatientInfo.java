package com.tentelemed.archipel.gam.domain;

import com.tentelemed.archipel.core.domain.model.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 14:26
 */
public class PatientInfo extends BaseVO {

    private final Address address;
    private final PhoneNumber mainPhone;
    private final PhoneNumber mobilePhone;
    private final Email email;

    public PatientInfo(Address address, Email email, PhoneNumber mainPhone, PhoneNumber mobilePhone) {
        validate("address", address);
        validate("email", email);
        validate("mainPhone", mainPhone);
        validate("mobilePhone", mobilePhone);
        this.address = address;
        this.email = email;
        this.mainPhone = mainPhone;
        this.mobilePhone = mobilePhone;
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
