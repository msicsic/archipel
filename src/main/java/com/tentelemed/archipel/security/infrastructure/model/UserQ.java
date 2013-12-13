package com.tentelemed.archipel.security.infrastructure.model;

import com.tentelemed.archipel.core.infrastructure.model.BaseEntityQ;
import com.tentelemed.archipel.security.application.event.UserRegistered;
import com.tentelemed.archipel.security.domain.model.RoleId;
import com.tentelemed.archipel.security.domain.model.UserId;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
public class UserQ extends BaseEntityQ<UserId> {
    @NotNull @Size(min = 2, max = 50) String firstName;
    @NotNull @Size(min = 2, max = 50) String lastName;
    @Temporal(TemporalType.TIME) Date dob;
    @Email String email = "default@mail.com";
    @NotNull String login;
    String password;
    RoleId roleId;

    public UserQ() {
    }

    @Override
    protected Class<UserId> getIdClass() {
        return UserId.class;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
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

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public RoleId getRoleId() {
        return roleId;
    }

    public void setRoleId(RoleId roleId) {
        this.roleId = roleId;
    }

    public void applyEvent(UserRegistered event) {
        this.dob = event.getDob();
        this.email = event.getEmail();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.id = event.getId().getId();
        this.login = event.getCredentials().getLogin();
        this.password = event.getCredentials().getPassword();
    }
}
