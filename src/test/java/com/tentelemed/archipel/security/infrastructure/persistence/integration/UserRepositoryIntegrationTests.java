package com.tentelemed.archipel.security.infrastructure.persistence.integration;


import com.tentelemed.archipel.core.infrastructure.config.SpringConfiguration;
import com.tentelemed.archipel.security.domain.interfaces.UserRepository;
import com.tentelemed.archipel.security.domain.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfiguration.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class UserRepositoryIntegrationTests {

    @Autowired
    UserRepository repository;

    /**
     * - recherche par LastName exact
     * - sensible a la casse
     *
     * @throws Exception
     */
    @Test
    public void thatFindByLoginWorks() throws Exception {
        for (int i=0; i<2; i++) {
            User user = User.createUser("Paul"+i, "Durand"+i, "login"+i, "password"+i);
            repository.save(user);
        }

        User user = repository.findByLogin("login1");
        assertNotNull(user);
        assertEquals(user.getFirstName(), "Paul1");

        user = repository.findByLogin("LoGin1");
        assertEquals(user, null);
    }

    @Test
    public void thatGetAllUsersWorks() throws Exception {
        for (int i=0; i<2; i++) {
            User user = User.createUser("Paul"+i, "Durand"+i, "login"+i, "password"+i);
            repository.save(user);
        }

        List<User> users = repository.getAllUsers();
        assertNotNull(users);
        assertEquals(users.size(), 2);
    }

    @Test
    public void thatFindByIdWorks() throws Exception {
        User user = User.createUser("Paul", "Durand", "login", "password");
        user = repository.save(user);
        assertNotNull(user.getEntityId());
        User user2 = repository.load(user.getEntityId());
        assertEquals(user, user2);
    }

    @Test
    public void thatDeleteWorks() throws Exception {
        User user = User.createUser("Paul", "Durand", "login", "password");
        user = repository.save(user);
        List<User> users = repository.getAllUsers();
        assertEquals(users.size(), 1);
        repository.deleteUser(user.getEntityId());
        users = repository.getAllUsers();
        assertEquals(users.size(), 0);
    }

}
