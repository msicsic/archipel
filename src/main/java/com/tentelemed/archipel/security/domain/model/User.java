package com.tentelemed.archipel.security.domain.model;

import com.google.common.base.Objects;
import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.DomainException;
import com.tentelemed.archipel.security.application.event.*;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 21/10/13
 * Time: 16:45
 */
public class User extends BaseAggregateRoot<UserId> implements UserEventHandler {

    public static class ChangePasswordException extends DomainException {
    }

    @NotNull Credentials credentials;
    @NotNull @Size(min = 2, max = 50) String firstName;
    @NotNull @Size(min = 2, max = 50) String lastName;
    @Email String email = "default@mail.com";
    Date dob;
    RoleId roleId;

    String generatePassword() {
        return "123456789";
    }

    // ********************* COMMANDS ****************************
    // ***********************************************************

    public List<DomainEvent> register(RoleId roleId, String firstName, String lastName, Date dob, String email, String login) {
        validate("firstName", firstName);
        validate("lastName", lastName);
        validate("dob", dob);
        validate("email", email);
        validate("login", login);
        return list(new UserRegistered(getEntityId(), roleId, firstName, lastName, dob, email, new Credentials(login, generatePassword())));
    }

    public List<DomainEvent> delete(UserId id) {
        validate("id", id);
        return list(new UserDeleted(id));
    }

    public List<DomainEvent> updateInfo(String firstName, String lastName, Date dob, String email) {
        validate("firstName", firstName);
        validate("lastName", lastName);
        validate("dob", dob);
        validate("email", email);
        return list(new UserInfoUpdated(getEntityId(), firstName, lastName, dob, email));
    }

    public List<DomainEvent> changePassword(String old, String new1, String new2) throws ChangePasswordException {
        if (!Objects.equal(old, getPassword()) || !Objects.equal(new1, new2)) {
            throw new ChangePasswordException();
        }
        return list(new UserPasswordUpdated(getEntityId(), new1));
    }

    public List<DomainEvent> changeRole(RoleId roleId) {
        return list(new UserRoleUpdated(getEntityId(), roleId));
    }


    // ********************* EVENTS ******************************
    // ***********************************************************

    public void handle(UserDeleted event) {
    }

    public void handle(UserRegistered event) {
        this.roleId = event.roleId;
        this.firstName = event.firstName;
        this.lastName = event.lastName;
        this.dob = event.dob;
        this.email = event.email;
        this.credentials = event.credentials;
    }

    public void handle(UserInfoUpdated event) {
        this.firstName = event.firstName;
        this.lastName = event.lastName;
        this.dob = event.dob;
        this.email = event.email;
    }

    public void handle(UserPasswordUpdated event) {
        this.credentials = new Credentials(credentials.getLogin(), event.password);
    }

    public void handle(UserRoleUpdated event) {
        this.roleId = event.roleId;
    }

    // ********************* ACCESSORS ***************************
    // ***********************************************************

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    protected Class<UserId> getIdClass() {
        return UserId.class;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return this.credentials.getLogin();
    }

    public String getPassword() {
        return this.credentials.getPassword();
    }

    public Date getDob() {
        return dob;
    }

    public RoleId getRoleId() {
        return roleId;
    }
}
