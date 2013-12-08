package com.tentelemed.archipel.core.domain.model;

import com.tentelemed.archipel.core.application.event.AbstractDomainEvent;
import com.tentelemed.archipel.core.infrastructure.domain.TestRoleQ;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 08/12/13
 * Time: 21:02
 */
public class TestUserRegistered extends AbstractDomainEvent<TestUserId> {
    private String firstName;
    private String lastName;
    private Date dob;
    private String login;
    private String password;
    private TestRoleQ role;

    TestUserRegistered() {}

    public TestUserRegistered(String firstName, String lastName, Date dob, String login, String password, TestRoleQ role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.login = login;
        this.password = password;
        this.role = role;
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

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public TestRoleQ getRole() {
        return role;
    }
}
