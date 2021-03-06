package com.tentelemed.archipel.security.infrastructure.web;

import com.google.common.eventbus.Subscribe;
import com.tentelemed.archipel.core.application.service.EventHandler;
import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.security.application.command.CmdUserCreate;
import com.tentelemed.archipel.security.application.command.CmdUserDelete;
import com.tentelemed.archipel.security.application.command.CmdUserUpdateInfo;
import com.tentelemed.archipel.security.application.command.UserCmdHandler;
import com.tentelemed.archipel.security.application.service.UserQueryService;
import com.tentelemed.archipel.security.domain.pub.EvtUserDomainEvent;
import com.tentelemed.archipel.security.domain.pub.RoleQ;
import com.tentelemed.archipel.security.domain.pub.UserQ;
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
@EventHandler
public class UiUsersModel extends BaseViewModel {

    @Autowired
    UserCmdHandler userCommand;

    @Autowired
    UserQueryService userQuery;

    @Valid
    UserQ selectedUser;
    private String nameFilter = "";

    public UserQ getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserQ selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<UserQ> getUsers() {
        return userQuery.getAllUsers();
    }

    public void action_commit() {
        try {
            commit();
            if (getSelectedUser().getId() == null) {
                UserQ u = getSelectedUser();
                RoleQ role = userQuery.getAnyRole();
                userCommand.execute(new CmdUserCreate(role.getEntityId(), u.getFirstName(), u.getLastName(), u.getDob(), u.getEmail(), u.getLogin()));
            } else {
                UserQ u = getSelectedUser();
                userCommand.execute(new CmdUserUpdateInfo(u.getEntityId(), u.getFirstName(), u.getLastName(), u.getDob(), u.getEmail()));
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
                userCommand.execute(new CmdUserDelete(getSelectedUser().getEntityId()));
            }
        });
    }

    public void action_add() {
        setSelectedUser(new UserQ());
    }

    @Subscribe
    public void handleEvent(EvtUserDomainEvent event) {
        if (event.isDelete()) {
            if (getSelectedUser() != null && Objects.equals(getSelectedUser().getId(), event.getId().getId())) {
                setSelectedUser(null);
            }
        } else if (event.isCreate() && getSelectedUser().getId() == null) {
            setSelectedUser(userQuery.getUser(event.getId()));
        }
    }

    public boolean isDeleteEnabled() {
        return getSelectedUser() != null;
    }

    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }
}
