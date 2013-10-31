package com.tentelemed.archipel.core.web;

import com.google.common.eventbus.EventBus;
import com.tentelemed.archipel.module.security.service.UserService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.xpoft.vaadin.VaadinView;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 28/10/13
 * Time: 16:19
 */
@Component
@Scope("prototype")
@VaadinView(MainView.NAME)
public class MainView extends CustomComponent implements View {

    public static final String NAME = "main";
    private Label text;

    @Autowired
    UserService service;

    @Autowired
    EventBus eventBus;

    @PostConstruct
    public void postConstruct() {
        text = new Label();
        Button logout = new Button("Logout", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                doLogout();
            }
        });

        setCompositionRoot(new CssLayout(text, logout));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // Get the user name from the session
        String username = String.valueOf(getSession().getAttribute("user"));

        // And show the username
        text.setValue("Hello " + username);
    }

    private void doLogout() {
        // "Logout" the user
        getSession().setAttribute("user", null);
        service.doLogout();
    }
}

