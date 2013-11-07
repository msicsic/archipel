package com.tentelemed.persistence.integration;

import com.tentelemed.archipel.core.infrastructure.config.SpringConfiguration;
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

import static com.tentelemed.persistence.domain.fixture.JPAAssertions.assertTableExists;
import static com.tentelemed.persistence.domain.fixture.JPAAssertions.assertTableHasColumn;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfiguration.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class UserMappingIntegrationTests {

    @Autowired
    EntityManager manager;

    /**
     * - La table créé doit respecter la NamingStrategy
     * @throws Exception
     */
    @Test
    public void thatTableCreationWorks() throws Exception {
        assertTableExists(manager, "t_user");
        assertTableHasColumn(manager, "t_user", "c_first_name");
        assertTableHasColumn(manager, "t_user", "c_credentials_login");
        assertTableHasColumn(manager, "t_user", "c_credentials_password");
    }

}