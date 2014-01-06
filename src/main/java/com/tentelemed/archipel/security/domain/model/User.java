package com.tentelemed.archipel.security.domain.model;

import com.google.common.base.Objects;
import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.DomainException;
import com.tentelemed.archipel.security.application.command.*;
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
public class User extends BaseAggregateRoot<UserId> implements UserCmdHandler {

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

    @Override
    public CmdRes execute(CmdUserCreate cmd) {
        validate("firstName", cmd.firstName);
        validate("lastName", cmd.lastName);
        validate("dob", cmd.dob);
        validate("email", cmd.email);
        validate("login", cmd.login);
        return _result(handle(new EvtUserRegistered(getEntityId(), cmd.roleId, cmd.firstName, cmd.lastName, cmd.dob, cmd.email, new Credentials(cmd.login, generatePassword()))));
    }

    @Override
    public CmdRes execute(CmdUserDelete cmd) {
        return _result(handle(new EvtUserDeleted(getEntityId())));
    }

    @Override
    public CmdRes execute(CmdUserUpdateInfo cmd) {
        validate("firstName", cmd.firstName);
        validate("lastName", cmd.lastName);
        validate("dob", cmd.dob);
        validate("email", cmd.email);
        return _result(handle(new EvtUserInfoUpdated(getEntityId(), cmd.firstName, cmd.lastName, cmd.dob, cmd.email)));
    }

    @Override
    public CmdRes execute(CmdUserChangePassword cmd) throws ChangePasswordException {
        if (!Objects.equal(cmd.old, getPassword()) || !Objects.equal(cmd.new1, cmd.new2)) {
            throw new ChangePasswordException();
        }
        return _result(handle(new EvtUserPasswordUpdated(getEntityId(), cmd.new1)));
    }

    /*public CmdRes changeRole(RoleId roleId) {
        return _result(handle(new EvtUserRoleUpdated(getEntityId(), roleId)));
    }*/


    // ********************* EVENTS ******************************
    // ***********************************************************

    EvtUserDeleted handle(EvtUserDeleted event) {
        // ras
        return null;
    }

    EvtUserRegistered handle(EvtUserRegistered event) {
        this.roleId = event.getRoleId();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.dob = event.getDob();
        this.email = event.getEmail();
        this.credentials = event.getCredentials();
        return handled(event);
    }

    EvtUserInfoUpdated handle(EvtUserInfoUpdated event) {
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.dob = event.getDob();
        this.email = event.getEmail();
        return handled(event);
    }

    EvtUserPasswordUpdated handle(EvtUserPasswordUpdated event) {
        this.credentials = new Credentials(credentials.getLogin(), event.getPassword());
        return handled(event);
    }

    EvtUserRoleUpdated handle(EvtUserRoleUpdated event) {
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
