package com.tentelemed.archipel.security.infrastructure.persistence.domain;

import com.tentelemed.archipel.core.infrastructure.domain.BaseEntityHb;
import com.tentelemed.archipel.security.domain.model.Credentials;
import com.tentelemed.archipel.security.domain.model.User;
import com.tentelemed.archipel.security.domain.model.UserId;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
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
public class UserHb extends BaseEntityHb {
    @NotNull @Size(min = 2, max = 50) String firstName;
    @NotNull @Size(min = 2, max = 50) String lastName;
    Date dob;
    @Email String email = "default@mail.com";
    @NotNull String login;
    String password;

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

    public static UserHb fromUser(User user) {
        UserHb res = new UserHb();
        res.setDob(user.getDob());
        res.setEmail(user.getEmail());
        res.setFirstName(user.getFirstName());
        res.setLastName(user.getLastName());
        return res;
    }

    public static User toUser(UserHb user) {
        User res = new User();
        res.register(new UserId(user.getId()), user.getFirstName(), user.getLastName(), user.getDob(), user.getEmail(), user.getLogin());
        return res;
    }

}
