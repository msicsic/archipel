package com.tentelemed.archipel.security.infrastructure.web;

import com.tentelemed.archipel.security.application.model.UserDTO;
import com.tentelemed.archipel.security.application.service.UserCommandService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 13/11/13
 * Time: 11:49
 */
public class UiUsersViewModelTest {

    @InjectMocks
    UiUsersViewModel model;

    @Mock
    UserCommandService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void thatCommitWorks() {

        // given
        UserDTO user = new UserDTO();
        model.setSelectedUser(user);

        // when
        model.action_commit();

        // then
        verify(service).updateUserInfo(user);
        assertTrue(model.isCommited());
    }

    @Test
    public void thatDeleteWorks() {

        // given
        UserDTO user = new UserDTO("123");
        model.setSelectedUser(user);

        // when
        model.action_delete();

        // then
        verify(service).deleteUser(user.getEntityId());
    }

    @Test
    public void thatCancelWorks() {

        // given
        UserDTO user = new UserDTO();
        model.setSelectedUser(user);

        // when
        model.action_discard();

        // then
        verify(service, never()).updateUserInfo(user);
        assertTrue(!model.isCommited());
        assertTrue(model.isDiscarded());
    }
}
