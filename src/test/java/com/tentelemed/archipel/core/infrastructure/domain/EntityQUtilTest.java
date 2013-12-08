package com.tentelemed.archipel.core.infrastructure.domain;

import com.tentelemed.archipel.core.domain.model.TestUserRegistered;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 05/12/13
 * Time: 19:14
 */
public class EntityQUtilTest {

    @Test
    public void testApplyEvent() throws Exception {

        // Given
        TestUserQ user = new TestUserQ();
        TestRoleQ role = new TestRoleQ();
        TestUserRegistered event = new TestUserRegistered("Paul", "Durand", new Date(), "login", "password", role);

        // When
        EntityQUtil.applyEvent(user, event);

        // Then
        assertThat(user.getFirstName(), equalTo(event.getFirstName()));
        assertThat(user.getRole(), equalTo(event.getRole()));
        assertThat(user.getRole().getName(), equalTo(event.getRole().getName()));

    }
}
