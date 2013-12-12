package com.tentelemed.archipel.medicalcenter.infrastructure.model;

import com.tentelemed.archipel.core.infrastructure.model.BaseEntityQ;
import com.tentelemed.archipel.medicalcenter.domain.model.Bank;
import com.tentelemed.archipel.medicalcenter.domain.model.BankId;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterId;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterType;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 12/12/13
 * Time: 14:25
 */
@Entity
public class MedicalCenterQ extends BaseEntityQ<MedicalCenterId> {
    @NotNull @Size(min=3) String name;
    @NotNull @Size(min=3) String ident;
    @NotNull MedicalCenterType type;
    String siret;
    String street;
    String town;
    String postalCode;
    String countryISO;
    String phone;
    String fax;
    String directorName;
    String bankCode;
    boolean emergenciesAvailable;
    boolean drugstoreAvailable;
    boolean privateRoomAvailable;

    @Override
    protected Class<MedicalCenterId> getIdClass() {
        return MedicalCenterId.class;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public MedicalCenterType getType() {
        return type;
    }

    public void setType(MedicalCenterType type) {
        this.type = type;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryISO() {
        return countryISO;
    }

    public void setCountryISO(String countryISO) {
        this.countryISO = countryISO;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public boolean isEmergenciesAvailable() {
        return emergenciesAvailable;
    }

    public void setEmergenciesAvailable(boolean emergenciesAvailable) {
        this.emergenciesAvailable = emergenciesAvailable;
    }

    public boolean isDrugstoreAvailable() {
        return drugstoreAvailable;
    }

    public void setDrugstoreAvailable(boolean drugstoreAvailable) {
        this.drugstoreAvailable = drugstoreAvailable;
    }

    public boolean isPrivateRoomAvailable() {
        return privateRoomAvailable;
    }

    public void setPrivateRoomAvailable(boolean privateRoomAvailable) {
        this.privateRoomAvailable = privateRoomAvailable;
    }
}
