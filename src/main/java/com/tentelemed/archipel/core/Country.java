package com.tentelemed.archipel.core;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 14:30
 */
@ValueObject
@Embeddable
public class Country {

    @NotNull
    @Size(min=3, max=3)
    String isoCode;

    public String getIsoCode() {
        return isoCode;
    }
}
