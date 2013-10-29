package com.tentelemed.archipel.web.view.module1;

import com.tentelemed.archipel.web.view.BasicView;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.xpoft.vaadin.VaadinView;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 28/10/13
 * Time: 14:13
 */
@Component
@Scope("prototype")
@VaadinView(Module1View.NAME)
@RequiresRoles({"user", "module1"})
public class Module1View extends BasicView<Module1ViewModel> {

    public final static String NAME = "module1";

    @Autowired
    Module1ViewModel model;

    @Override
    protected Module1ViewModel getModel() {
        return model;
    }

    @PostConstruct
    public void postConstruct() {

        setSizeFull();

        // Create error message label
        Label label = new Label();
        bind(label, "text");

        // Create login button
        Button loginButton = new Button("Login");
        bind(loginButton, "doLogin");

        // Add both to a panel
        VerticalLayout fields = new VerticalLayout(label);
        fields.setCaption("Please login to access the application. (test@test.com/passw0rd)");
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

        // The view root layout
        VerticalLayout viewLayout = new VerticalLayout(fields);
        viewLayout.setSizeFull();
        viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
        viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
        setCompositionRoot(viewLayout);

    }
}
