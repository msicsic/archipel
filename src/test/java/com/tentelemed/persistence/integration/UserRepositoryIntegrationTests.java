package com.tentelemed.persistence.integration;


import com.tentelemed.archipel.core.infrastructure.config.SpringConfiguration;
import com.tentelemed.archipel.security.domain.model.User;
import com.tentelemed.archipel.security.infrastructure.persistence.UserRepositoryUtil;
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
    UserRepositoryUtil repository;

    /**
     * - recherche par LastName exact
     * - sensible a la casse
     *
     * @throws Exception
     */
    @Test
    public void thatFindByLastNameWorks() throws Exception {

        for (int i=0; i<2; i++) {
            User user = User.createUser("Paul"+i, "Durand"+i, "login"+i, "password"+i);
            repository.save(user);
        }

        List<User> retrievedUsers = repository.findByLastName("Durand1");
        assertNotNull(retrievedUsers);
        assertEquals(retrievedUsers.size(), 1);
        assertEquals(retrievedUsers.get(0).getFirstName(), "Paul1");

        retrievedUsers = repository.findByLastName("durand1");
        assertEquals(retrievedUsers.size(), 0);
    }

    /**
     * - recherche par FirstName ou LastName contains
     * - insensible a la casse
     * @throws Exception
     */
    @Test
    public void thatFindByNameWorks() throws Exception {
        for (int i=0; i<2; i++) {
            User user = User.createUser("Paul"+i, "Durand"+i, "login"+i, "password"+i);
            repository.save(user);
        }
        List<User> retrievedUsers = repository.findByName("Durand");
        assertEquals(retrievedUsers.size(), 2);

        retrievedUsers = repository.findByName("durand");
        assertEquals(retrievedUsers.size(), 2);
    }

}
