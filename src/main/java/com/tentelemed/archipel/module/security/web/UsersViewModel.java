package com.tentelemed.archipel.module.security.web;

import com.tentelemed.archipel.core.web.BasicViewModel;
import com.tentelemed.archipel.module.security.domain.User;
import com.tentelemed.archipel.module.security.service.UserService;
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
public class UsersViewModel extends BasicViewModel {

    @Autowired
    UserService userService;

    User selectedUser;

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
}
