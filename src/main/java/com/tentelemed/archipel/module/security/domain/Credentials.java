package com.tentelemed.archipel.module.security.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 21/10/13
 * Time: 16:45
 */
@Embeddable
public class Credentials {

    //@Getter
    @NotNull @Size(min = 5)
    String login;

    //@Getter
    @NotNull @Size(min = 5)
    String password;

    Credentials() {
    }

    public Credentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public boolean matchPassword(String password) {
        return Objects.equals(this.password, password);
    }

    public boolean match(String login, String password) {
        return Objects.equals(this.password, password) &&
                Objects.equals(this.login, login) ;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
