package com.tentelemed.archipel.security.domain.model;

import com.google.common.base.Objects;
import com.tentelemed.archipel.core.application.DomainEvent;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.DomainException;
import com.tentelemed.archipel.security.application.event.UserDeleted;
import com.tentelemed.archipel.security.application.event.UserInfoUpdated;
import com.tentelemed.archipel.security.application.event.UserInitialized;
import com.tentelemed.archipel.security.application.model.UserDTO;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 21/10/13
 * Time: 16:45
 */
@Entity
public class User extends BaseAggregateRoot<UserId> {

    public static class ChangePasswordException extends DomainException {}

    @Embedded
    @NotNull
    Credentials credentials;

    @NotNull
    @Size(min = 2, max = 50)
    String firstName;

    @NotNull
    @Size(min = 2, max = 50)
    String lastName;

    Date dob;

    @Email
    String email = "default@mail.com";

    protected User() {
        super(UserId.class);
    }

    public static User createEmptyUser() {
        return new User();
    }

    public static User createUser(String firstName, String lastName, String login, String password) {
        User user = new User();
        user.firstName = firstName;
        user.lastName = lastName;
        user.dob = new Date();
        user.credentials = new Credentials(login, password);
        return user;
    }

    public void changePassword(String old, String new1, String new2) throws ChangePasswordException {
        if (!Objects.equal(old, getPassword()) || !Objects.equal(new1, new2)) {
            throw new ChangePasswordException();
        }
        if (credentials.matchPassword(old)) {
            credentials = new Credentials(credentials.getLogin(), new1);
        }
    }


    static long count = 0;

    protected UserId createId() {
        UserId res = new UserId();
        res.setId(""+count++);
        return res;
    }

    private String generatePassword() {
        return "123456789";
    }

    // ********************* COMMANDS ****************************
    // ***********************************************************

    public List<DomainEvent<UserId>> delete(UserId id) {
        return list(new UserDeleted(id));
    }

    public List<DomainEvent<UserId>> updateInfo(UserDTO info) {
        return list(new UserInfoUpdated(getEntityId(), info));
    }

    public List<DomainEvent<UserId>> initialise(UserDTO userDto) {
        // creation d'un ID
        UserId id = createId();

        // generation d'un mdp
        String password = generatePassword();

        // creation de l'evt
        return list(new UserInitialized(id, userDto, password));
    }

    // ********************* EVENTS ******************************
    // ***********************************************************

    public void handle(UserDeleted event) {
    }

    public void handle(UserInfoUpdated event) {
        this.firstName = event.getInfo().getFirstName();
        this.lastName = event.getInfo().getLastName();
        this.dob = event.getInfo().getDob();
        this.email = event.getInfo().getEmail();
    }

    public void handle(UserInitialized event) {
        this.id = event.getId().getId();
        this.firstName = event.getInfo().getFirstName();
        this.lastName = event.getInfo().getLastName();
        this.dob = event.getInfo().getDob();
        this.email = event.getInfo().getEmail();
        this.credentials = new Credentials(event.getInfo().getLogin(), event.getPassword());
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

    public Date getDob() {
        return dob;
    }
}
