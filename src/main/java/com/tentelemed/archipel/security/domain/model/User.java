package com.tentelemed.archipel.security.domain.model;

import com.google.common.base.Objects;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.DomainException;
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

    //@Getter
    @NotNull
    @Size(min = 2, max = 50)
    String firstName;

    //@Getter
    @NotNull
    @Size(min = 2, max = 50)
    String lastName;

    //@Getter
    @Email
    String email = "default@mail.com";

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

    // ********************* ACCESSORS ***************************
    // ***********************************************************

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

}
