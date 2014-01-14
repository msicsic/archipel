package com.tentelemed.archipel.core.infrastructure.model;

import com.tentelemed.archipel.core.domain.model.TestUserId;
import com.tentelemed.archipel.core.domain.pub.BaseEntityQ;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 08/12/13
 * Time: 20:57
 */
public class TestUserQ extends BaseEntityQ<TestUserId> {
    @NotNull private String firstName;
    @NotNull private String lastName;
    private Date dob;
    @NotNull private TestRoleQ role;
    @NotNull private String login;
    @NotNull private String password;


    @Override
    protected Class<TestUserId> getIdClass() {
        return TestUserId.class;
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

    public TestRoleQ getRole() {
        return role;
    }

    public void setRole(TestRoleQ role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
