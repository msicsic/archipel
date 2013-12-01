package com.tentelemed.archipel.gam.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BaseView;
import com.tentelemed.archipel.core.infrastructure.web.ModuleRoot;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 28/10/13
 * Time: 14:13
 */
@Component
@Scope("prototype")
@RequiresRoles({"user", "module1"})
@ModuleRoot(UiGamView.NAME)
public class UiGamView extends BaseView<UiGamViewModel> {

    public final static String NAME = "gam";

    @Autowired
    UiGamViewModel model;

    @Override
    public UiGamViewModel getModel() {
        return model;
    }

    @PostConstruct
    public void postConstruct() {

        setSizeFull();

        // The view root layout
        VerticalLayout viewLayout = new VerticalLayout();
        viewLayout.setSizeFull();
        viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
        setCompositionRoot(viewLayout);

        viewLayout.addComponent(new Label("GAM"));
        viewLayout.addComponent(new Label("GAM"));
        viewLayout.addComponent(new Label("GAM"));

    }

}
