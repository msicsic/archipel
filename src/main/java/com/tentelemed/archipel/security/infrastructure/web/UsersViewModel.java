package com.tentelemed.archipel.security.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BasicViewModel;
import com.tentelemed.archipel.security.application.model.UserDTO;
import com.tentelemed.archipel.security.application.service.UserServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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
    UserServiceAdapter userService;

    UserDTO selectedUser;

    public UserDTO getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserDTO selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    public void action_edit() {
      // setSelectedUser(null);
    }

    @NotNull @Size(min=3)
    public String getLogin() {
        return getSelectedUser().getLogin();
    }

    public void setLogin(String login) {
        getSelectedUser().setLogin(login);
    }

    @NotNull @Size(min=3)
    public String getFirstName() {
        return getSelectedUser().getFirstName();
    }

    public void setFirstName(String firstName) {
        getSelectedUser().setFirstName(firstName);
    }

    @NotNull @Size(min=3)
    public String getLastName() {
        return getSelectedUser().getLastName();
    }

    public void setLastName(String name) {
        getSelectedUser().setLastName(name);
    }
}
