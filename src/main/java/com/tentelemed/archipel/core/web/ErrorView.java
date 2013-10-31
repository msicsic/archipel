package com.tentelemed.archipel.core.web;

import com.tentelemed.archipel.module.security.web.LoginView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.xpoft.vaadin.VaadinMessageSource;
import ru.xpoft.vaadin.VaadinView;

import javax.annotation.PostConstruct;
import java.security.GeneralSecurityException;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 28/10/13
 * Time: 14:05
 */
@Component
@Scope("prototype")
@VaadinView(ErrorView.NAME)
public class ErrorView extends Panel implements View {

    public final static String NAME = "error";

    @Autowired
    VaadinMessageSource msg;

    @PostConstruct
    public void postConstruct() throws GeneralSecurityException {
        setSizeFull();
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);

        layout.addComponent(new Label("ERROR"));
        layout.addComponent(new Button(msg.getMessage("main.go_back"), new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Page.getCurrent().setUriFragment("!" + LoginView.NAME);
            }
        }));
        setContent(layout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
