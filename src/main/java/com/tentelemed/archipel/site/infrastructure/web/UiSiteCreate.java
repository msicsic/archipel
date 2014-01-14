package com.tentelemed.archipel.site.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BasePopup;
import com.tentelemed.archipel.site.domain.pub.SiteQ;
import com.tentelemed.archipel.site.domain.pub.SiteType;
import com.vaadin.data.Property;
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
public class UiSiteCreate extends BasePopup<UiSiteCreateModel> {

    @Autowired UiSiteCreateModel model;

    public UiSiteCreate() {
    }

    @Override
    public UiSiteCreateModel getModel() {
        return model;
    }

    @Override
    public void postConstruct() {
        setImmediate(true);
    }

    @Override
    public void onDisplay() {
        setCaption(model.isEdit() ? "Edit Center" : "Create Center");

        setModal(true);
        setResizable(false);

        setWidth("250px");
        setHeight("250px");
        center();

        VerticalLayout grid = new VerticalLayout();
        grid.setHeight("100%");
        FormLayout layout = new FormLayout();

        final ComboBox cb = new ComboBox("Type");
        cb.setImmediate(true);
        cb.setInvalidAllowed(false);
        cb.setNullSelectionAllowed(false);
        for (SiteType type : SiteType.values()) {
            cb.addItem(type);
        }
        cb.setTextInputAllowed(false);
        if (model.isEdit()) {
            cb.setValue(model.getCmdUpdate().type);
        }
        cb.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (model.isEdit()) {
                    model.getCmdUpdate().type = ((SiteType) cb.getValue());
                } else {
                    model.getCmdCreate().type = (((SiteType) cb.getValue()));
                }
            }
        });
        layout.addComponent(cb);
        if (model.isEdit()) {
            layout.addComponent(bind(new TextField("Name"), "cmdUpdate.name"));
            layout.addComponent(bind(new TextField("Ident"), "cmdUpdate.ident"));
        } else {
            layout.addComponent(bind(new TextField("Name"), "cmdCreate.name"));
            layout.addComponent(bind(new TextField("Ident"), "cmdCreate.ident"));
        }

        HorizontalLayout btLayout = new HorizontalLayout();
        btLayout.setSpacing(true);
        Button bt1 = bind(new Button(model.isEdit() ? "Edit Center" : "Create Center"), "createCenter");
        Button bt2 = bind(new Button("Cancel"), "cancel");
        btLayout.addComponent(bt1);
        btLayout.addComponent(bt2);

        grid.addComponent(layout);

        grid.addComponent(btLayout);
        grid.setComponentAlignment(btLayout, Alignment.BOTTOM_RIGHT);

        grid.setMargin(true);
        setContent(grid);
    }

    public void setCenter(SiteQ center) {
        model.setCenter(center);
    }
}
