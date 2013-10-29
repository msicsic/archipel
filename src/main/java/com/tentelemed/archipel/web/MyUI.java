package com.tentelemed.archipel.web;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tentelemed.archipel.module.security.service.UserService;
import com.tentelemed.archipel.web.view.NavigationEvent;
import com.tentelemed.archipel.web.view.ViewEvent;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.xpoft.vaadin.DiscoveryNavigator;
import ru.xpoft.vaadin.security.ShiroSecurityNavigator;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 25/10/13
 * Time: 14:18
 */
@Component
//@Scope("prototype")
@Scope("session")
@Title("Login frame")
public class MyUI extends UI {

    @Autowired
    EventBus eventBus;

    private DiscoveryNavigator navigator;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setSizeFull();
        //navigator = new ShiroSecurityNavigator(this, this);
        navigator = new DiscoveryNavigator(this, this);

        eventBus.register(this);
    }

    @Subscribe
    public void handleViewEvent(NavigationEvent event) {
        navigator.navigateTo(event.getViewId());
    }
}
