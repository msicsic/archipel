package com.tentelemed.archipel.security.infrastructure.persistence.integration;


import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.infrastructure.config.SpringConfiguration;
import com.tentelemed.archipel.security.domain.model.UserId;
import com.tentelemed.archipel.security.domain.interfaces.UserRepository;
import com.tentelemed.archipel.security.domain.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfiguration.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class UserRepositoryIntegrationTests {

    Logger log = LoggerFactory.getLogger(UserRepositoryIntegrationTests.class);

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
            User user = new User();
            user.register(new UserId(i + ""), "Paul" + i, "Durand" + i, new Date(), "mail@mail.com", "login" + i);
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
            User user = new User();
            user.register(new UserId(i + ""), "Paul" + i, "Durand" + i, new Date(), "mail@mail.com", "login" + i);
            repository.save(user);
        }

        List<User> users = repository.getAllUsers();
        assertNotNull(users);
        assertEquals(users.size(), 2);
    }

    @Test
    public void thatFindByIdWorks() throws Exception {
        User user = new User();
        user.register(new UserId("1"), "Paul", "Durand", new Date(), "mail@mail.com", "login");
        user = repository.save(user);
        assertNotNull(user.getEntityId());
        User user2 = repository.load(user.getEntityId());
        assertEquals(user, user2);
    }

    @Test
    public void thatDeleteWorks() throws Exception {
        User user = new User();
        user.register(new UserId("1"), "Paul", "Durand", new Date(), "mail@mail.com", "login");
        user = repository.save(user);
        List<User> users = repository.getAllUsers();
        assertEquals(users.size(), 1);
        repository.deleteUser(user.getEntityId());
        users = repository.getAllUsers();
        assertEquals(users.size(), 0);
    }

}
