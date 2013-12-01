package com.tentelemed.archipel.security.application.event;

import com.tentelemed.archipel.security.domain.model.UserId;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:10
 */
public class UserInfoUpdated extends UserDomainEvent {

    private final UserId entityId;
    private final String firstName;
    private final String lastName;
    private final Date dob;
    private final String email;

    public UserInfoUpdated(UserId entityId, String firstName, String lastName, Date dob, String email) {

        this.entityId = entityId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
    }

    public UserId getEntityId() {
        return entityId;
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
}
