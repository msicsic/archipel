package com.tentelemed.archipel.site.domain.model;

import com.tentelemed.archipel.core.domain.model.Address;
import com.tentelemed.archipel.core.domain.model.BaseVO;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:47
 */
@Embeddable
public class SiteInfo extends BaseVO {
    @NotNull String siret;
    @NotNull @Embedded Address address;
    String phone;
    String fax;
    String directorName;
    String bankCode;
    @Column(nullable = true) boolean emergenciesAvailable;
    @Column(nullable = true) boolean pharmacyAvailable;
    @Column(nullable = true) boolean privateRoomAvailable;

    SiteInfo() {
    }

    public SiteInfo(String siret, Address address, String phone, String fax, String directorName, String bankCode, boolean emergenciesAvailable, boolean pharmacyAvailable, boolean privateRoomAvailable) {
        this.siret = siret;
        this.address = address;
        this.phone = phone;
        this.fax = fax;
        this.directorName = directorName;
        this.bankCode = bankCode;
        this.emergenciesAvailable = emergenciesAvailable;
        this.pharmacyAvailable = pharmacyAvailable;
        this.privateRoomAvailable = privateRoomAvailable;
        validate();
    }


    public String getSiret() {
        return siret;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getFax() {
        return fax;
    }

    public String getDirectorName() {
        return directorName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public boolean isEmergenciesAvailable() {
        return emergenciesAvailable;
    }

    public boolean isPharmacyAvailable() {
        return pharmacyAvailable;
    }

    public boolean isPrivateRoomAvailable() {
        return privateRoomAvailable;
    }
}
