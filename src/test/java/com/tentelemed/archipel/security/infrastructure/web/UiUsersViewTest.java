package com.tentelemed.archipel.security.infrastructure.web;

import com.tentelemed.archipel.infrastructure.web.BaseViewTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.xpoft.vaadin.VaadinMessageSource;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 13/11/13
 * Time: 11:49
 */
public class UiUsersViewTest extends BaseViewTest<UiUsersView, UiUsersViewModel> {

    @InjectMocks
    UiUsersView view;

    @Mock
    UiUsersViewModel model;

    @Mock
    VaadinMessageSource msg;

    @Override
    protected UiUsersView getView() {
        return view;
    }

    @Override
    protected UiUsersViewModel getViewModel() {
        return model;
    }

    @Test
    public void thatBindingWorks() {
        super.thatBindingWorksWorks();
    }
}


