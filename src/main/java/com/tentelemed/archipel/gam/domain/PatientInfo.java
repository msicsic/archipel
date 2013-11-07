package com.tentelemed.archipel.gam.domain;

import com.tentelemed.archipel.core.domain.model.Country;
import com.tentelemed.archipel.core.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 14:26
 */
@ValueObject
@Embeddable
public class PatientInfo {
    @NotNull
    String town;

    @NotNull
    String address;

    @Embedded
    Country nationality;

}
