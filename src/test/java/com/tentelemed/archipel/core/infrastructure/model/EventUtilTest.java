package com.tentelemed.archipel.core.infrastructure.model;

import com.tentelemed.archipel.core.domain.model.TestUserId;
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
public class EventUtilTest {

    @Test
    public void testApplyEvent() throws Exception {

        // Given
        TestUserQ user = new TestUserQ();
        TestRoleQ role = new TestRoleQ();
        TestUserRegistered event = new TestUserRegistered(new TestUserId(11), "Paul", "Durand", new Date(), "login", "password", role);

        // When
        EventUtil.applyEvent(user, event);

        // Then
        assertThat(user.getFirstName(), equalTo(event.getFirstName()));
        assertThat(user.getRole(), equalTo(event.getRole()));
        assertThat(user.getRole().getName(), equalTo(event.getRole().getName()));
        assertThat(user.getId(), equalTo(11));
    }
}
