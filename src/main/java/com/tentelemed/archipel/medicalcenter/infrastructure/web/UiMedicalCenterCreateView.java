package com.tentelemed.archipel.medicalcenter.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BasePopup;
import com.tentelemed.archipel.core.infrastructure.web.BaseView;
import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.core.infrastructure.web.ModuleRoot;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterType;
import com.tentelemed.archipel.medicalcenter.infrastructure.model.MedicalCenterQ;
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
public class UiMedicalCenterCreateView extends BasePopup<UiMedicalCenterCreateViewModel> {

    @Autowired UiMedicalCenterCreateViewModel model;

    public UiMedicalCenterCreateView() {
        super("Create Center");
    }

    @Override
    public UiMedicalCenterCreateViewModel getModel() {
        return model;
    }

    @Override
    public void postConstruct() {
        //setImmediate(true);
        setModal(true);

        setWidth("300px");
        setHeight("320px");
        center();

        VerticalLayout grid = new VerticalLayout();
        FormLayout layout = new FormLayout();

        final ComboBox cb = new ComboBox("Type");
        cb.setImmediate(true);
        cb.setInvalidAllowed(false);
        cb.setNullSelectionAllowed(false);
        for (MedicalCenterType type : MedicalCenterType.values()) {
            cb.addItem(type);
        }
        cb.setTextInputAllowed(false);
        //cb.setBuffered(false);
        cb.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                model.getCmd().type = ((MedicalCenterType) cb.getValue());
            }
        });
        layout.addComponent(cb);
        layout.addComponent(bind(new TextField("Name"), "cmd.name"));
        layout.addComponent(bind(new TextField("Ident"), "cmd.ident"));

        layout.addComponent(bind(new Button("Create Center"), "createCenter"));
        layout.addComponent(bind(new Button("Cancel"), "cancel"));
        grid.addComponent(layout);

        grid.setMargin(true);
        setContent(grid);
    }
}
