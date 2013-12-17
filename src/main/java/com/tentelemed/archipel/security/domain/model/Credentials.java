package com.tentelemed.archipel.security.domain.model;

import com.tentelemed.archipel.core.domain.model.BaseVO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 21/10/13
 * Time: 16:45
 */
public class Credentials extends BaseVO {

    @NotNull @Size(min = 5) String login;
    @NotNull @Size(min = 5) String password;

    Credentials() {
    }

    public Credentials(String login, String password) {
        this.login = validate("login", login);
        this.password = validate("password", password);
    }

    public boolean matchPassword(String password) {
        return Objects.equals(this.password, password);
    }

    public boolean match(String login, String password) {
        return Objects.equals(this.password, password) &&
                Objects.equals(this.login, login);
    }

    // ********************* ACCESSORS ***************************
    // ***********************************************************

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
