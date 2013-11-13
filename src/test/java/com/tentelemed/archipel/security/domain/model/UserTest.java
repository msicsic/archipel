package com.tentelemed.archipel.security.domain.model;

import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;


/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 13/11/13
 * Time: 16:44
 */
public class UserTest {

    @Test
    public void testUpdateInfo() throws Exception {
        User user = new User();
        Date date = new Date();
        user.updateInfo("firstName", "lastName", "email", date);
        assertEquals(user.getFirstName(), "firstName");
        assertEquals(user.getLastName(), "lastName");
        assertEquals(user.getEmail(), "email");
        assertEquals(user.getDob(), date);
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = User.createUser("firstName", "lastName", "login", "password");
        assertEquals(user.getFirstName(), "firstName");
        assertEquals(user.getLastName(), "lastName");
        assertEquals(user.getLogin(), "login");
        assertEquals(user.getPassword(), "password");
    }

    @Test(expected = User.ChangePasswordException.class)
    public void testChangePasswordWithBadOldPwd() throws Exception {
        User user = User.createUser("firstName", "lastName", "login", "password");
        user.changePassword("wrongold", "aaa", "aaa");
    }

    @Test
    public void testChangePasswordOk() throws Exception {
        User user = User.createUser("firstName", "lastName", "login", "password");
        user.changePassword("password", "newPass1", "newPass1");
    }

    @Test(expected = User.ChangePasswordException.class)
    public void testChangePasswordBadNewPwd() throws Exception {
        User user = User.createUser("firstName", "lastName", "login", "password");
        user.changePassword("password", "newPass1", "newPass99");
    }
}
