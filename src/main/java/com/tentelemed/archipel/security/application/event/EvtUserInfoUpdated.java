package com.tentelemed.archipel.security.application.event;

import com.tentelemed.archipel.security.domain.model.UserId;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:10
 */
public class EvtUserInfoUpdated extends EvtUserDomainEvent {

    private String firstName;
    private String lastName;
    private Date dob;
    private String email;

    EvtUserInfoUpdated() {
    }

    public EvtUserInfoUpdated(UserId entityId, String firstName, String lastName, Date dob, String email) {
        super(entityId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
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
