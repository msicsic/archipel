package com.tentelemed.archipel.module.security.event.domain;

import com.tentelemed.archipel.module.security.domain.User;
import com.tentelemed.archipel.module.security.domain.UserId;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 06/11/13
 * Time: 17:15
 */
public class UserDTO {
    @NotNull
    UserId id;

    @NotNull
    String login;

    @NotNull
    String firstName;

    @NotNull
    String lastName;

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

    public UserId getId() {
        return id;
    }

    public void setId(UserId id) {
        this.id = id;
    }

    public static UserDTO fromUser(User user) {
        UserDTO u = new UserDTO();
        try {
            BeanUtils.copyProperties(u, user);
            return u;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
