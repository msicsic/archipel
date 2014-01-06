package com.tentelemed.archipel.site.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BasePopup;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 11/12/13
 * Time: 13:55
 */
@Component
@Scope("prototype")
public class UiSiteCreateActivityUnit extends BasePopup<UiSiteCreateActivityUnitModel> {

    @Autowired UiSiteCreateActivityUnitModel model;

    @Override
    public UiSiteCreateActivityUnitModel getModel() {
        return model;
    }

    @Override
    public void postConstruct() {
    }

    @Override
    public void onDisplay() {
        setCaption("Create Activity Unit");

        setModal(true);
        setResizable(false);

        setWidth("250px");
        setHeight("250px");
        center();

        VerticalLayout grid = new VerticalLayout();
        grid.setHeight("100%");
        FormLayout layout = new FormLayout();

        layout.addComponent(new Label("This Activity Unit will be added to Functional Unit : " + model.getUnit().getName()));
        layout.addComponent(bind(new TextField("Name"), "cmd.name"));
        layout.addComponent(bind(new TextField("Code"), "cmd.code"));

        HorizontalLayout btLayout = new HorizontalLayout();
        btLayout.setSpacing(true);
        Button bt1 = bind(new Button("Create"), "create");
        Button bt2 = bind(new Button("Cancel"), "cancel");
        btLayout.addComponent(bt1);
        btLayout.addComponent(bt2);

        grid.addComponent(layout);
        grid.addComponent(btLayout);
        grid.setComponentAlignment(btLayout, Alignment.BOTTOM_RIGHT);

        grid.setMargin(true);
        setContent(grid);
    }
}
