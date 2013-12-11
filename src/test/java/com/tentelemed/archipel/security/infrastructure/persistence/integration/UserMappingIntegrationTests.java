package com.tentelemed.archipel.security.infrastructure.persistence.integration;

import com.tentelemed.archipel.infrastructure.config.TestSpringConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.tentelemed.archipel.domain.fixture.JPAAssertions.assertTableExists;
import static com.tentelemed.archipel.domain.fixture.JPAAssertions.assertTableHasColumn;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestSpringConfiguration.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class UserMappingIntegrationTests {

    @Autowired
    EntityManager manager;

    /**
     * - La table créé doit respecter la NamingStrategy
     *
     * @throws Exception
     */
    @Test
    public void thatTableCreationWorks() throws Exception {
        assertTableExists(manager, "t_userq");
        assertTableHasColumn(manager, "t_userq", "c_first_name");
        assertTableHasColumn(manager, "t_userq", "c_login");
        assertTableHasColumn(manager, "t_userq", "c_password");
    }

}