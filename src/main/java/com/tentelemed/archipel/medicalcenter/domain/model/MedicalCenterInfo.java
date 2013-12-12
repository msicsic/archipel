package com.tentelemed.archipel.medicalcenter.domain.model;

import com.tentelemed.archipel.core.domain.model.Address;
import com.tentelemed.archipel.core.domain.model.BaseVO;
import com.tentelemed.archipel.core.domain.model.PhoneNumber;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:47
 */
public class MedicalCenterInfo extends BaseVO {
    @NotNull String siret;
    @NotNull Address address;
    @Valid PhoneNumber phone;
    @Valid PhoneNumber fax;
    String directorName;
    Bank bank;
    boolean emergenciesAvailable;
    boolean drugstoreAvailable;
    boolean privateRoomAvailable;

    MedicalCenterInfo() {}

    public MedicalCenterInfo(String siret, Address address, PhoneNumber phone, PhoneNumber fax, String directorName, Bank bank, boolean emergenciesAvailable, boolean drugstoreAvailable, boolean privateRoomAvailable) {
        this.siret = siret;
        this.address = address;
        this.phone = phone;
        this.fax = fax;
        this.directorName = directorName;
        this.bank = bank;
        this.emergenciesAvailable = emergenciesAvailable;
        this.drugstoreAvailable = drugstoreAvailable;
        this.privateRoomAvailable = privateRoomAvailable;
        validate();
    }


    public String getSiret() {
        return siret;
    }

    public Address getAddress() {
        return address;
    }

    public PhoneNumber getPhone() {
        return phone;
    }

    public PhoneNumber getFax() {
        return fax;
    }

    public String getDirectorName() {
        return directorName;
    }

    public Bank getBank() {
        return bank;
    }

    public boolean isEmergenciesAvailable() {
        return emergenciesAvailable;
    }

    public boolean isDrugstoreAvailable() {
        return drugstoreAvailable;
    }

    public boolean isPrivateRoomAvailable() {
        return privateRoomAvailable;
    }
}
