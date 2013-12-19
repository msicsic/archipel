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
public class UiUsersTest extends BaseViewTest<UiUsers, UiUsersModel> {

    @InjectMocks
    UiUsers view;

    @Mock
    UiUsersModel model;

    @Mock
    VaadinMessageSource msg;

    @Override
    protected UiUsers getView() {
        return view;
    }

    @Override
    protected UiUsersModel getViewModel() {
        return model;
    }

    @Test
    public void thatBindingWorks() {
        super.thatBindingWorksWorks();
    }
}


