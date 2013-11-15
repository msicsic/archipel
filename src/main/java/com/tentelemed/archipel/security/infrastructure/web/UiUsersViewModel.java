package com.tentelemed.archipel.security.infrastructure.web;

import com.google.common.eventbus.Subscribe;
import com.tentelemed.archipel.core.application.service.EventListener;
import com.tentelemed.archipel.core.infrastructure.web.BasicViewModel;
import com.tentelemed.archipel.security.application.event.SecUserDeletedEvent;
import com.tentelemed.archipel.security.application.model.UserDTO;
import com.tentelemed.archipel.security.application.service.UserServiceAdapter;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/10/13
 * Time: 10:42
 */
@Component
@Scope("prototype")
@EventListener
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
            if (getSelectedUser().getEntityId() == null) {
                userService.createUser(getSelectedUser());
            } else {
                userService.updateUserInfo(getSelectedUser());
            }
            show("User committed");
        } catch (FieldGroup.CommitException e) {
            show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
        } catch (Exception e) {
            show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    public void action_discard() {
        discard();
    }

    public void action_delete() {
        userService.deleteUser(getSelectedUser().getEntityId());
    }

    public void action_add() {
        setSelectedUser(new UserDTO("-", "-", "-", "-", null));
    }

    // Il faut également notifier la vue pour qu'elle se mette a jour (ligne a retirer)
    // TODO : BUG !!! l'instance n'est pas la meme que celle associée a la vue !
    @Subscribe
    public void handleEvent(SecUserDeletedEvent event) {
        if (getSelectedUser() != null && Objects.equals(getSelectedUser().getEntityId(), event.getUserId())) {
            setSelectedUser(null);
        }
    }

    public boolean isDeleteEnabled() {
        return getSelectedUser() != null;
    }

}
