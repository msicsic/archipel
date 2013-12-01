package com.tentelemed.archipel.security.infrastructure.persistence.domain;

import com.tentelemed.archipel.core.infrastructure.domain.BaseEntityHb;
import com.tentelemed.archipel.security.domain.model.Credentials;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 01/12/13
 * Time: 01:13
 */
@Entity
public class UserHb extends BaseEntityHb {
    @NotNull Credentials credentials;
    @NotNull @Size(min = 2, max = 50) String firstName;
    @NotNull @Size(min = 2, max = 50) String lastName;
    Date dob;
    @Email String email = "default@mail.com";

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
