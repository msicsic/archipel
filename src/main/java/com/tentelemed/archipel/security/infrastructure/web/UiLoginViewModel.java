package com.tentelemed.archipel.security.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.security.application.service.UserQueryService;
import com.vaadin.data.fieldgroup.FieldGroup;
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
public class UiLoginViewModel extends BaseViewModel {

    @Autowired
    UserQueryService userQuery;

    @NotNull @Size(min = 3)
    String userName = "login1";

    @NotNull @Size(min = 3)
    String password = "123456789";

    String error = "";

    public void action_doLogin() {
        try {
            commit();
            userQuery.doLogin(userName, password);
            showError("ok");
        } catch (UnknownAccountException e) {
            showError(gt("unknownAccount") + " " + userName);
        } catch (AuthenticationException e) {
            showError(gt("badCredentials") + " " + userName);
        } catch (FieldGroup.CommitException e) {
            showError("Validation Exception");
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
