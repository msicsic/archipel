package com.tentelemed.archipel.web.view;

import com.tentelemed.archipel.web.component.ChooseLanguage;
import com.tentelemed.archipel.web.view.login.LoginView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
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

    @Autowired
    ChooseLanguage chooseLanguage;

    @PostConstruct
    public void postConstruct() throws GeneralSecurityException {
        setSizeFull();
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);

        layout.addComponent(chooseLanguage);

        layout.addComponent(new Label(msg.getMessage("error_view.reload")));

        layout.addComponent(new Button(msg.getMessage("error_view.turn_off"), new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Notification.show("one");
            }
        }));

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
