package com.tentelemed.archipel.security.infrastructure.web;

import com.tentelemed.archipel.security.application.command.CmdUserDelete;
import com.tentelemed.archipel.security.application.command.CmdUserUpdateInfo;
import com.tentelemed.archipel.security.application.service.UserCommandService;
import com.tentelemed.archipel.security.domain.model.UserId;
import com.tentelemed.archipel.security.infrastructure.model.UserQ;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 13/11/13
 * Time: 11:49
 */
public class UiUsersModelTest {

    @InjectMocks
    UiUsersModel model;

    @Mock
    UserCommandService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void thatCommitWorks() {

        // given
        UserQ user = new UserQ();
        user.setId(1);
        model.setSelectedUser(user);

        // when
        model.action_commit();

        // then
        verify(service).execute(new CmdUserUpdateInfo(any(UserId.class), user.getFirstName(), user.getLastName(), user.getDob(), user.getEmail()));
        assertTrue(model.isCommited());
    }

    @Test
    public void thatDeleteWorks() {

        // given
        UserQ user = new UserQ();
        model.setSelectedUser(user);

        // when
        model.action_delete();

        // then
        verify(service).execute(new CmdUserDelete(any(UserId.class)));
    }

    @Test
    public void thatCancelWorks() {

        // given
        UserQ user = new UserQ();
        model.setSelectedUser(user);

        // when
        model.action_discard();

        // then
        verify(service, never()).execute(new CmdUserUpdateInfo(user.getEntityId(), user.getFirstName(), user.getLastName(), user.getDob(), user.getEmail()));
        assertTrue(!model.isCommited());
        assertTrue(model.isDiscarded());
    }
}
