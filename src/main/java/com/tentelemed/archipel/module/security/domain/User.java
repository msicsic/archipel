package com.tentelemed.archipel.module.security.domain;

import com.google.common.base.Objects;
import com.tentelemed.archipel.core.BaseAggregateRoot;
import com.tentelemed.archipel.core.DomainException;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 21/10/13
 * Time: 16:45
 */
@Entity
public class User extends BaseAggregateRoot<UserId> {

    protected User() {
        super(UserId.class);
    }

    public static class ChangePasswordException extends DomainException {
    }

    @Embedded
    @NotNull
    Credentials credentials;

    @NotNull
    @Size(min = 2, max = 50)
    String firstName;

    @NotNull
    @Size(min = 2, max = 50)
    String lastName;

    @Email
    String email;

    public String getLogin() {
        return this.credentials.getLogin();
    }

    public static User createUser(String firstName, String lastName, String login, String password) {
        User user = new User();
        user.firstName = firstName;
        user.lastName = lastName;
        user.credentials = new Credentials(login, password);
        return user;
    }

    public void changeName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void changePassword(String old, String new1, String new2) throws ChangePasswordException {
        if (!Objects.equal(new1, new2)) {
            throw new ChangePasswordException();
        }
        if (credentials.matchPassword(old)) {
            credentials = new Credentials(credentials.getLogin(), new1);
        }
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
}
