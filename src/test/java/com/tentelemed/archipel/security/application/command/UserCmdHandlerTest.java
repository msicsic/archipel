package com.tentelemed.archipel.security.application.command;

import com.tentelemed.archipel.core.application.command.CmdRes;
import com.tentelemed.archipel.core.application.service.CmdHandlerTest;
import com.tentelemed.archipel.security.domain.model.Role;
import com.tentelemed.archipel.security.domain.model.User;
import com.tentelemed.archipel.security.domain.pub.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 07/01/14
 * Time: 14:02
 */
public class UserCmdHandlerTest extends CmdHandlerTest {

    UserCmdHandler userCmdHandler;
    RoleCmdHandler roleCmdHandler;

    @Before
    public void setup() {
        super.setup();
        userCmdHandler = context.getBean(UserCmdHandler.class);
        roleCmdHandler = context.getBean(RoleCmdHandler.class);
    }

    @Test
    public void executeCmdUserCreate() {
        // Given
        Date date = new Date();
        CmdUserCreate cmd = new CmdUserCreate(new RoleId(111), "Paul", "Durand", date, "mail@mail.com", "login");

        // When
        CmdRes res = userCmdHandler.execute(cmd);

        // Then (User dans eventstore et correctement initialisé)
        User user = (User) eventStore.get((UserId)res.entityId);
        assertThat(user, notNullValue());
        assertThat(user.getEntityId(), notNullValue());
        assertThat(user.getEmail(), equalTo("mail@mail.com"));
        assertThat(user.getFirstName(), equalTo("Paul"));
        assertThat(user.getLastName(), equalTo("Durand"));
        assertThat(user.getLogin(), equalTo("login"));
        assertThat(user.getPassword(), notNullValue());

        // Then (Evt correctement généré)
        EvtUserRegistered evt = res.getEvent(EvtUserRegistered.class);
        assertThat(evt, notNullValue());
        assertThat(evt.getId(), equalTo(user.getEntityId()));
        assertThat(evt.getEmail(), equalTo(user.getEmail()));
        assertThat(evt.getLastName(), equalTo(user.getLastName()));
        assertThat(evt.getFirstName(), equalTo(user.getFirstName()));
        assertThat(evt.getCredentials().getLogin(), equalTo(user.getLogin()));
        assertThat(evt.getCredentials().getPassword(), equalTo(user.getPassword()));
        assertThat(evt.getDob(), equalTo(user.getDob()));

        // Then (UserQ correctement généré)
        UserQ userQ = pHandler.find(UserQ.class, user.getEntityId().getId());
        assertThat(userQ, notNullValue());
        assertThat(userQ.getDob(), equalTo(date));
        assertThat(userQ.getPassword(), equalTo(user.getPassword()));
        assertThat(userQ.getLogin(), equalTo(user.getLogin()));
        assertThat(userQ.getLastName(), equalTo(user.getLastName()));
        assertThat(userQ.getFirstName(), equalTo(user.getFirstName()));
        assertThat(userQ.getRoleId(), equalTo(user.getRoleId()));
        assertThat(userQ.getEmail(), equalTo(user.getEmail()));
    }

    @Test
    public void executeCmdUserDelete() {
        // Given (user existe)
        userCmdHandler.execute(new CmdUserCreate(new RoleId(111), "Paul", "Durand", new Date(), "mail@mail.com", "login"));

        // When (delete)
        CmdRes res = userCmdHandler.execute(new CmdUserDelete(new UserId(0)));

        // Then (l'objet n'est plus dans le store ni dans le repo)
        Object user = eventStore.get(res.entityId);
        assertThat(user, nullValue());
        user = pHandler.find(UserQ.class, res.entityId.getId());
        assertThat(user, nullValue());
    }

    @Test
    public void executeCmdUserUpdateInfo() {
        // Given (user existant)
        Date date1 = new Date();
        userCmdHandler.execute(new CmdUserCreate(new RoleId(111), "Paul", "Durand", date1, "mail@mail.com", "login"));

        // When (exec commande)
        Date date2 = new Date();
        CmdRes res = userCmdHandler.execute(new CmdUserUpdateInfo(new UserId(0), "Paul2", "Durand2", date2, "mail2@mail.com"));

        // Then (objet User modifié)
        User user = (User) eventStore.get((UserId)res.entityId);
        assertThat(user.getEmail(), equalTo("mail2@mail.com"));
        assertThat(user.getFirstName(), equalTo("Paul2"));
        assertThat(user.getLastName(), equalTo("Durand2"));
        assertThat(user.getDob(), equalTo(date2));

        // Then (objet UserQ modifié)
        UserQ userQ = pHandler.find(UserQ.class, user.getEntityId().getId());
        assertThat(userQ.getEmail(), equalTo(user.getEmail()));
        assertThat(userQ.getFirstName(), equalTo(user.getFirstName()));
        assertThat(userQ.getLastName(), equalTo(user.getLastName()));
        assertThat(userQ.getDob(), equalTo(user.getDob()));
    }

    @Test
    public void executeCmdUserChangePassword_ok() {
        // Given (user existe)
        CmdRes res = userCmdHandler.execute(new CmdUserCreate(new RoleId(111), "Paul", "Durand", new Date(), "mail@mail.com", "login"));
        UserId userId = (UserId) res.entityId;
        User user = (User) eventStore.get(userId);

        // When (exec commande)
        res = userCmdHandler.execute(new CmdUserChangePassword(user.getEntityId(), user.getPassword(), "newPass", "newPass"));

        // Then (evt levé)
        assertThat(res.getEvent(EvtUserPasswordUpdated.class), notNullValue());

        // Then (User mot de passe modifié)
        user = (User) eventStore.get(userId);
        assertThat(user.getPassword(), equalTo("newPass"));

        // Then (UserQ mot de passe modifié)
        UserQ userQ = pHandler.find(UserQ.class, user.getEntityId().getId());
        assertThat(userQ.getPassword(), equalTo("newPass"));

    }

    @Test(expected = User.ChangePasswordException.class)
    public void executeCmdUserChangePassword_bad_last_password() {
        // Given (user existe)
        CmdRes res = userCmdHandler.execute(new CmdUserCreate(new RoleId(111), "Paul", "Durand", new Date(), "mail@mail.com", "login"));
        User user = (User) eventStore.get((UserId)res.entityId);

        // When (exec commande password ko)
        userCmdHandler.execute(new CmdUserChangePassword(user.getEntityId(), "badPassword", "newPass", "newPass"));

        // Then (exception)
    }

    @Test(expected = User.ChangePasswordException.class)
    public void executeCmdUserChangePassword_bad_new_password() {
        // Given (user existe)
        CmdRes res = userCmdHandler.execute(new CmdUserCreate(new RoleId(111), "Paul", "Durand", new Date(), "mail@mail.com", "login"));
        User user = (User) eventStore.get((UserId)res.entityId);

        // When (exec commande new password ko)
        userCmdHandler.execute(new CmdUserChangePassword(user.getEntityId(), user.getPassword(), "newPass", "badNewPass"));

        // Then (exception)
    }

    @Test
    public void executeCmdRoleCreate() {
        // Given (rien)

        // When (exec commande)
        CmdRes res = roleCmdHandler.execute(new CmdRoleCreate("RoleName", Right.GLOBAL_ADMIN));

        // Then (Evt levé)
        assertThat(res.getEvent(EvtRoleRegistered.class), notNullValue());

        // Then (Role présent dans eventManager)
        Role role = (Role) eventStore.get(new RoleId(0));
        assertThat(role, notNullValue());
        assertThat(role.getName(), equalTo("RoleName"));
        assertThat(role.getRights().iterator().next().name(), equalTo("GLOBAL_ADMIN"));

        // Then (RoleQ présent dans bdd)
        RoleQ roleQ = pHandler.find(RoleQ.class, role.getEntityId().getId());
        assertThat(roleQ, notNullValue());
        assertThat(roleQ.getName(), equalTo("RoleName"));
        assertThat(roleQ.getRights().iterator().next().name(), equalTo("GLOBAL_ADMIN"));
    }

    @Test
    public void executeCmdRoleDelete() {
        // Given (Role présent)
        roleCmdHandler.execute(new CmdRoleCreate("RoleName", Right.GLOBAL_ADMIN));

        // When (exec commande)
        CmdRes res = roleCmdHandler.execute(new CmdRoleDelete(new RoleId(0)));

        // Then (evt levé)
        assertThat(res.getEvent(EvtRoleDeleted.class), notNullValue());

        // Then (Role effacé)
        assertThat(eventStore.get(new RoleId(0)), nullValue());

        // Then (RoleQ effacé)
        assertThat(pHandler.find(RoleQ.class, new RoleId(0).getId()), nullValue());
    }

    @Test
    public void executeCmdRoleUpdateRights() {
        // Given (Role présent)
        roleCmdHandler.execute(new CmdRoleCreate("RoleName", Right.GLOBAL_ADMIN));

        // When
        CmdRes res = roleCmdHandler.execute(new CmdRoleUpdateRights(new RoleId(0), Right.SITES_ADMIN));

        // Then (Evt levé)
        assertThat(res.getEvent(EvtRoleRightsUpdated.class), notNullValue());

        // Then (Role à jour)
        Role role = (Role) eventStore.get(new RoleId(0));
        assertThat(role.getRights().contains(Right.GLOBAL_ADMIN), equalTo(false));
        assertThat(role.getRights().contains(Right.SITES_ADMIN), equalTo(true));

        // Then (RoleQ à jour)
        RoleQ roleQ = pHandler.find(RoleQ.class, new RoleId(0).getId());
        assertThat(roleQ.getRights().contains(Right.GLOBAL_ADMIN), equalTo(false));
        assertThat(roleQ.getRights().contains(Right.SITES_ADMIN), equalTo(true));
    }
}
