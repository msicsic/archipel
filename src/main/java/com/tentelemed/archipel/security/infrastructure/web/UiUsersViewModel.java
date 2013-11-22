package com.tentelemed.archipel.security.infrastructure.web;

import com.google.common.eventbus.Subscribe;
import com.tentelemed.archipel.core.application.service.EventListener;
import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.security.application.event.UserDomainEvent;
import com.tentelemed.archipel.security.application.model.UserDTO;
import com.tentelemed.archipel.security.application.service.UserCommandService;
import com.tentelemed.archipel.security.application.service.UserQueryService;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.*;
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
public class UiUsersViewModel extends BaseViewModel {

    @Autowired
    UserCommandService userCommand;

    @Autowired
    UserQueryService userQuery;

    @Valid
    UserDTO selectedUser;

    public UserDTO getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserDTO selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<UserDTO> getUsers() {
        return userQuery.getAllUsers();
    }

    public void action_commit() {
        try {
            commit();
            if (getSelectedUser().getEntityId() == null) {
                userCommand.registerUser(getSelectedUser());
                //setSelectedUser(null);
            } else {
                userCommand.updateUserInfo(getSelectedUser());
            }
            show("User committed");
        } catch (FieldGroup.CommitException e) {
            show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(null, e);
            show(e);
        }
    }

    public void action_discard() {
        discard();
    }

    public void action_delete() {
        confirm("Confirm dialog", "Please confirm that you want to delete this User", new Runnable() {
            @Override
            public void run() {
                userCommand.deleteUser(getSelectedUser().getEntityId());
            }
        });
    }

    public void action_add() {
        setSelectedUser(new UserDTO("-", "-", "-", "-", null));
    }

    @Subscribe
    public void handleEvent(UserDomainEvent event) {
        if (event.isDelete()) {
            if (getSelectedUser() != null && Objects.equals(getSelectedUser().getEntityId(), event.getAggregateId())) {
                setSelectedUser(null);
            }
        } else if (event.isCreate() && getSelectedUser().getEntityId() == null) {
            setSelectedUser(userQuery.getUser(event.getAggregateId()));
        }
    }

    public boolean isDeleteEnabled() {
        return getSelectedUser() != null;
    }

}
