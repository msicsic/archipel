package com.tentelemed.archipel.module.security.web;

import com.tentelemed.archipel.module.security.service.UserService;
import com.tentelemed.archipel.core.web.BasicViewModel;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/10/13
 * Time: 10:42
 */
@Component
@Scope("prototype")
public class LoginViewModel extends BasicViewModel {

    @Autowired
    UserService userService;

    @NotNull @Size(min=3)
    String userName = "";

    @NotNull @Size(min=3)
    String password = "";

    String error = "";

    public void action_doLogin() {
        try {
            userService.doLogin(userName, password);
        } catch (UnknownAccountException e) {
            showError(gt("unknownAccount")+" "+userName);
        } catch (AuthenticationException e) {
            showError(gt("badCredentials")+" "+userName);
        }
    }

    private void showError(String msg) {
        this.error = msg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getError() {
        return error;
    }
}
