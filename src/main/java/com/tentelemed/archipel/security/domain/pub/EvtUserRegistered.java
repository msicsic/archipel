package com.tentelemed.archipel.security.domain.pub;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:10
 */
public class EvtUserRegistered extends EvtUserDomainEvent {

    @Override
    public Type getCrudType() {
        return Type.CREATE;
    }

    private String firstName;
    private String lastName;
    private Date dob;
    private String email;
    private Credentials credentials;
    private RoleId roleId;

    EvtUserRegistered() {
    }

    public EvtUserRegistered(UserId id, RoleId roleId, String firstName, String lastName, Date dob, String email, Credentials credentials) {
        super(id);
        this.roleId = roleId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
        this.credentials = credentials;
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

    public RoleId getRoleId() {
        return roleId;
    }
}
