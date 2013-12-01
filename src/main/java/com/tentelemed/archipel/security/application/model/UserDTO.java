package com.tentelemed.archipel.security.application.model;

import com.tentelemed.archipel.core.application.model.BaseDTO;
import com.tentelemed.archipel.security.domain.model.UserId;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 06/11/13
 * Time: 17:15
 */
public class UserDTO extends BaseDTO<UserId> {

    @NotNull
    Date dob;

    @NotNull @Size(min=3)
    String login;

    @NotNull @Size(min=2)
    String firstName;

    @NotNull @Size(min=2)
    String lastName;

    @Email
    String email;

    public UserDTO() {
    }

    public UserDTO(String s) {
        super(s);
    }

    public UserDTO(String firstName, String lastName, String login, String email, Date dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.dob = dob;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getFullName() {
        return getFirstName()+" "+getLastName();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    protected Class<UserId> getIdClass() {
        return UserId.class;
    }
}
