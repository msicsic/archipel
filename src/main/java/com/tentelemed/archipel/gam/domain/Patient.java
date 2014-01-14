package com.tentelemed.archipel.gam.domain;

import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.Country;
import com.tentelemed.archipel.security.domain.pub.UserId;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 12:04
 */
public class Patient extends BaseAggregateRoot<PatientId> {

    public static enum Sex {MALE, FEMALE}

    public static enum Status {ALIVE, DEAD}

    public static enum FamillyStatus {SINGLE, MARRIED, DIVORCED}

    public static enum IdentityStatus {VERIFIED, NOT_VERIFIED}

    public static enum DobType {NORMAL, APPROX, MOON}

    @NotNull @Size(min = 2, max = 50) String firstName;
    @NotNull @Size(min = 2, max = 50) String firstName2;
    @NotNull @Size(min = 2, max = 50) String lastName;
    boolean confidential;
    @NotNull IPP IPP;
    @NotNull Sex sex;
    @NotNull Date birthDate;
    @Size(min = 2, max = 50) String birthName;
    @NotNull Status status = Status.ALIVE;
    @NotNull Date dateOfDeath;
    @NotNull UserId creator;
    @NotNull UserId updator;
    @NotNull Country birthCountry;
    @NotNull String birthTown;
    String job;
    @Valid PatientInfo info;

    @Override
    protected Class<PatientId> getIdClass() {
        return PatientId.class;
    }

}
