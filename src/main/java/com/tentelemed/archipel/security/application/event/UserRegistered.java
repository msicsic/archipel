package com.tentelemed.archipel.security.application.event;

import com.tentelemed.archipel.security.domain.model.Credentials;
import com.tentelemed.archipel.security.domain.model.RoleId;
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
    public Type getCrudType() {
        return Type.CREATE;
    }

    private String firstName;
    private String lastName;
    private Date dob;
    private String email;
    private Credentials credentials;
    private RoleId roleId;

    UserRegistered() {}

    public UserRegistered(UserId id, RoleId roleId, String firstName, String lastName, Date dob, String email, Credentials credentials) {
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
