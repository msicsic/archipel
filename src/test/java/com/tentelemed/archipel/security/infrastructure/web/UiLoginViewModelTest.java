package com.tentelemed.archipel.security.infrastructure.web;

import com.tentelemed.archipel.security.application.service.UserQueryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.verify;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 13/11/13
 * Time: 11:49
 */
public class UiLoginViewModelTest {

    @InjectMocks
    UiLoginViewModel model;

    @Mock
    UserQueryService userQuery;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void thatActionLoginWorks() {

        // given
        model.setUserName("login1");
        model.setPassword("password1");

        // when
        model.action_doLogin();

        // then
        verify(userQuery).doLogin("login1", "password1");
        assertTrue(model.isCommited());
    }
}
