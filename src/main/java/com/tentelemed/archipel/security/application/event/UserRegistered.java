package com.tentelemed.archipel.security.application.event;

import com.tentelemed.archipel.security.domain.model.Credentials;
import com.tentelemed.archipel.security.domain.model.UserId;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:10
 */
public class UserRegistered extends UserDomainEvent {

    @Override
    public Type getType() {
        return Type.CREATE;
    }

    public final String firstName;
    public final String lastName;
    public final Date dob;
    public final String email;
    public final Credentials credentials;

    public UserRegistered(UserId id, String firstName, String lastName, Date dob, String email, Credentials credentials) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
        this.credentials = credentials;
    }
}
