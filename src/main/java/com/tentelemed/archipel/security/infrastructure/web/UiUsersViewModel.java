package com.tentelemed.archipel.security.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BasicViewModel;
import com.tentelemed.archipel.security.application.model.UserDTO;
import com.tentelemed.archipel.security.application.service.UserServiceAdapter;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/10/13
 * Time: 10:42
 */
@Component
@Scope("prototype")
public class UiUsersViewModel extends BasicViewModel {

    @Autowired
    UserServiceAdapter userService;

    @Valid
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

    public void action_commit() {
        try {
            commit();
            userService.updateUserInfo(getSelectedUser());
            Notification.show("User committed");
        } catch (FieldGroup.CommitException e) {
            Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
        } catch (Exception e) {
            Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    public void action_discard() {
        discard();
    }

    public void action_delete() {
        userService.deleteUser(getSelectedUser().getEntityId());
    }

    public void action_add() {
        // TODO
    }

    // TODO : ecouter l'event "userDeleted" pour mettre la selection courante a null
}
