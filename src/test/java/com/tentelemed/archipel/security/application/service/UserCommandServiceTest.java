package com.tentelemed.archipel.security.application.service;

import com.googlecode.zohhak.api.Coercion;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.core.infrastructure.persistence.handler.TestPersistenceHandler;
import com.tentelemed.archipel.infrastructure.config.TestSpringConfiguration2;
import com.tentelemed.archipel.security.application.command.*;
import com.tentelemed.archipel.security.application.event.EvtUserRegistered;
import com.tentelemed.archipel.security.domain.model.RoleId;
import com.tentelemed.archipel.security.domain.model.User;
import com.tentelemed.archipel.security.domain.model.UserId;
import com.tentelemed.archipel.security.infrastructure.model.UserQ;
import com.tentelemed.archipel.security.infrastructure.model.UserQEventHandler;
import com.tentelemed.gam.domain.TestEventStore;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 07/01/14
 * Time: 14:02
 */
@RunWith(ZohhakRunner.class)
public class UserCommandServiceTest implements UserCmdHandler {

    static AnnotationConfigWebApplicationContext context;
    TestPersistenceHandler pHandler;
    UserCmdHandler cmdHandler;
    TestEventStore eventStore;

    @BeforeClass
    public static void init() {
        context = new AnnotationConfigWebApplicationContext();
        context.register(TestSpringConfiguration2.class);
        context.register(UserCommandService.class);
        context.register(UserQEventHandler.class);
        context.refresh();
    }

    @Before
    public void setup() {
        cmdHandler = context.getBean(UserCmdHandler.class);
        pHandler = context.getBean(TestPersistenceHandler.class);
        pHandler.clear();
        eventStore = context.getBean(TestEventStore.class);
        eventStore.clear();
    }

    @Override
    @TestWith("null")
    public CmdRes execute(CmdUserCreate cmd) {
        // Given
        Date date = new Date();
        cmd = new CmdUserCreate(new RoleId(111), "Paul", "Durand", date, "mail@mail.com", "login");

        // When
        CmdRes res = cmdHandler.execute(cmd);

        // Then (User dans eventstore et correctement initialisé)
        User user = (User) eventStore.get((UserId)res.aggregate.getEntityId());
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
        return null;
    }

    @Override
    @TestWith("null")
    public CmdRes execute(CmdUserDelete cmd) {
        // Given (user existe)
        cmdHandler.execute(new CmdUserCreate(new RoleId(111), "Paul", "Durand", new Date(), "mail@mail.com", "login"));

        // When (delete)
        CmdRes res = cmdHandler.execute(new CmdUserDelete(new UserId(0)));

        // Then (l'objet n'est plus dans le store ni dans le repo)
        Object user = eventStore.get(res.aggregate.getEntityId());
        assertThat(user, nullValue());
        user = pHandler.find(UserQ.class, res.aggregate.getEntityId().getId());
        assertThat(user, nullValue());
        return null;

        // TODO : le eventStore ne doit plus contenir l'objet apres un delete
    }

    @Override
    public CmdRes execute(CmdUserUpdateInfo cmd) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CmdRes execute(CmdUserChangePassword cmd) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
