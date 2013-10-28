package com.tentelemed.archipel.web;

import com.tentelemed.archipel.module.security.service.UserService;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.xpoft.vaadin.DiscoveryNavigator;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 25/10/13
 * Time: 14:18
 */
@Component
//@Scope("session")
@Scope("prototype")
//@Theme("myTheme")
@Title("Login frame")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setSizeFull();
        DiscoveryNavigator navigator = new DiscoveryNavigator(this, this);
    }
}
