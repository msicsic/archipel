package com.tentelemed.archipel.security.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BaseView;
import com.tentelemed.archipel.core.infrastructure.web.ModuleRoot;
import com.vaadin.event.ShortcutAction;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
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
@ModuleRoot(value = UiLogin.NAME, login = true)
public class UiLogin extends BaseView<UiLoginModel> {

    public final static String NAME = "login";

    @Autowired
    UiLoginModel model;

    @Override
    public UiLoginModel getModel() {
        return model;
    }

    public void onDisplay() {

        setSizeFull();

        // Create the user input field
        TextField user = new TextField(gt("login"));
        user.setStyleName("big");
        user.setWidth("200px");
        user.setInputPrompt(gt("loginPrompt"));
        bind(user, "userName");
        //user.setInvalidAllowed(false);

        // Create the password input field
        PasswordField password = new PasswordField(gt("password"));
        password.setWidth("200px");
        password.setNullRepresentation("");
        bind(password, "password");

        // Create error message label
        Label error = new Label();
        error.setStyleName("error");
        bind(error, "error");

        // Create login button
        Button loginButton = new Button(gt("loginBt"));
        loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        loginButton.setStyleName("default");
        bind(loginButton, "doLogin");

        // Info Label
        Label infoLabel = new Label();
        bind(infoLabel, "info");

        // Add both to a panel
        VerticalLayout fields = new VerticalLayout(user, password, error, loginButton, infoLabel);
        fields.setCaption(gt("caption"));
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, false, false, false));
        fields.setSizeUndefined();

        // The view root layout
        VerticalLayout viewLayout = new VerticalLayout(fields);
        viewLayout.setSizeFull();
        viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
        viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
        setCompositionRoot(viewLayout);

        // focus the username field when user arrives to the login view
        user.focus();
    }
}
