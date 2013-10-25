package com.tentelemed.archipel.module.gam.domain;

import com.tentelemed.archipel.core.BaseAggregateRoot;
import com.tentelemed.archipel.core.Country;
import com.tentelemed.archipel.module.security.domain.UserId;

import javax.persistence.Embedded;
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

    protected Patient() {
        super(PatientId.class);
    }

    @NotNull
    @Size(min = 2, max = 50)
    String firstName;

    @NotNull
    @Size(min = 2, max = 50)
    String lastName;

    @NotNull
    Sex sex;

    @NotNull
    Date birthDate;

    @Size(min = 2, max = 50)
    String birthName;

    @NotNull
    Status status = Status.ALIVE;

    @NotNull
    Date dateOfDeath;

    @NotNull
    UserId creator;

    @NotNull
    UserId updator;

    @NotNull
    Country birthCountry;

    @NotNull
    String birthTown;

    @Embedded
    @Valid
    PatientInfo info;



}
