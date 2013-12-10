package com.tentelemed.archipel.security.domain.model;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;


/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 13/11/13
 * Time: 16:44
 */
public class UserTest {
    Logger log = LoggerFactory.getLogger(UserTest.class);

    protected <U extends BaseAggregateRoot> U handle(U object, List<? extends DomainEvent> events) {
        for (DomainEvent event : events) {
            try {
                Method method = object.getClass().getMethod("handle", event.getClass());
                method.invoke(object, event);
            } catch (Exception e) {
                log.error(null, e);
            }
        }
        return object;
    }


    @Test
    public void testUpdateInfo() throws Exception {
        User user = new User();
        Date date = new Date();
        handle(user, user.register(new RoleId(1), "firstName", "lastName", new Date(), "mail@mail.com", "login1"));
        handle(user, user.updateInfo("firstName", "lastName", date, "email99@mail.com"));
        assertEquals(user.getFirstName(), "firstName");
        assertEquals(user.getLastName(), "lastName");
        assertEquals(user.getEmail(), "email99@mail.com");
        assertEquals(user.getDob(), date);
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        handle(user, user.register(new RoleId(1), "firstName", "lastName", new Date(), "mail@mail.com", "login1"));
        assertEquals(user.getFirstName(), "firstName");
        assertEquals(user.getLastName(), "lastName");
        assertEquals(user.getLogin(), "login1");
    }

    @Test(expected = User.ChangePasswordException.class)
    public void testChangePasswordWithBadOldPwd() throws Exception {
        User user = new User();
        handle(user, user.register(new RoleId(1), "firstName", "lastName", new Date(), "mail@mail.com", "login1"));
        handle(user, user.changePassword("wrongold", "aaa", "aaa"));
    }

    @Test
    public void testChangePasswordOk() throws Exception {
        User user = new User();
        handle(user, user.register(new RoleId(1), "firstName", "lastName", new Date(), "mail@mail.com", "login1"));
        String password = user.getPassword();
        handle(user, user.changePassword(password, "newPass1", "newPass1"));
    }

    @Test(expected = User.ChangePasswordException.class)
    public void testChangePasswordBadNewPwd() throws Exception {
        User user = new User();
        handle(user, user.register(new RoleId(1), "firstName", "lastName", new Date(), "mail@mail.com", "login1"));
        handle(user, user.changePassword("password", "newPass1", "newPass99"));
    }
}
