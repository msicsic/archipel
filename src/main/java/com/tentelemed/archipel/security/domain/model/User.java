package com.tentelemed.archipel.security.domain.model;

import com.google.common.base.Objects;
import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.DomainException;
import com.tentelemed.archipel.security.application.event.*;
import org.hibernate.validator.constraints.Email;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 21/10/13
 * Time: 16:45
 */
public class User extends BaseAggregateRoot<UserId> {

    public static class ChangePasswordException extends DomainException {
    }

    @NotNull @Valid Credentials credentials;
    @NotNull @Size(min = 2, max = 50) String firstName;
    @NotNull @Size(min = 2, max = 50) String lastName;
    @NotNull @Email String email = "default@mail.com";
    Date dob;
    @NotNull RoleId roleId;

    String generatePassword() {
        return "123456789";
    }

    // ********************* COMMANDS ****************************
    // ***********************************************************

    public CmdRes register(RoleId roleId, String firstName, String lastName, Date dob, String email, String login) {
        validate("firstName", firstName);
        validate("lastName", lastName);
        validate("dob", dob);
        validate("email", email);
        validate("login", login);
        return _result(handle(new UserRegistered(getEntityId(), roleId, firstName, lastName, dob, email, new Credentials(login, generatePassword()))));
    }

    public CmdRes delete() {
        return _result(handle(new UserDeleted(getEntityId())));
    }

    public CmdRes updateInfo(String firstName, String lastName, Date dob, String email) {
        validate("firstName", firstName);
        validate("lastName", lastName);
        validate("dob", dob);
        validate("email", email);
        return _result(handle(new UserInfoUpdated(getEntityId(), firstName, lastName, dob, email)));
    }

    public CmdRes changePassword(String old, String new1, String new2) throws ChangePasswordException {
        if (!Objects.equal(old, getPassword()) || !Objects.equal(new1, new2)) {
            throw new ChangePasswordException();
        }
        return _result(handle(new UserPasswordUpdated(getEntityId(), new1)));
    }

    public CmdRes changeRole(RoleId roleId) {
        return _result(handle(new UserRoleUpdated(getEntityId(), roleId)));
    }


    // ********************* EVENTS ******************************
    // ***********************************************************

    public UserDeleted handle(UserDeleted event) {
        // ras
        return null;
    }

    public UserRegistered handle(UserRegistered event) {
        this.roleId = event.getRoleId();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.dob = event.getDob();
        this.email = event.getEmail();
        this.credentials = event.getCredentials();
        return handled(event);
    }

    public UserInfoUpdated handle(UserInfoUpdated event) {
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.dob = event.getDob();
        this.email = event.getEmail();
        return handled(event);
    }

    public UserPasswordUpdated handle(UserPasswordUpdated event) {
        this.credentials = new Credentials(credentials.getLogin(), event.getPassword());
        return handled(event);
    }

    public UserRoleUpdated handle(UserRoleUpdated event) {
        this.roleId = event.getRoleId();
        return handled(event);
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
