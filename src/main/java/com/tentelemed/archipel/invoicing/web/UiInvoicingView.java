package com.tentelemed.archipel.invoicing.web;

import com.tentelemed.archipel.core.infrastructure.web.BaseView;
import com.tentelemed.archipel.core.infrastructure.web.ModuleRoot;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 28/10/13
 * Time: 14:13
 */
@Component
@Scope("prototype")
@RequiresRoles({"user", "module2"})
@ModuleRoot(UiInvoicingView.NAME)
public class UiInvoicingView extends BaseView<UiInvoicingViewModel> {

    public final static String NAME = "invoicing";

    @Autowired
    UiInvoicingViewModel model;

    @Override
    public UiInvoicingViewModel getModel() {
        return model;
    }

    public void postConstruct() {

        setSizeFull();

        // The view root layout
        VerticalLayout viewLayout = new VerticalLayout();
        viewLayout.setSizeFull();
        viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
        setCompositionRoot(viewLayout);

        viewLayout.addComponent(new Label("INVOICING"));

    }

}
