package com.tentelemed.archipel.security.application.event;

import com.tentelemed.archipel.security.domain.model.UserId;
import com.tentelemed.archipel.security.domain.model.Credentials;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:10
 */
public class UserRegistered extends UserDomainEvent {


    private final UserId id;
    private final String firstName;
    private final String lastName;
    private final Date dob;
    private final String email;
    private final Credentials credentials;

    public UserRegistered(UserId id, String firstName, String lastName, Date dob, String email, Credentials credentials) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
        this.credentials = credentials;
    }

    public UserId getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public Credentials getCredentials() {
        return credentials;
    }

}
