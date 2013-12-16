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
    private Button bt1;
    private Button bt2;

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

        HorizontalLayout btLayout = new HorizontalLayout();
        btLayout.setSpacing(true);
        bt1 = bind(new Button("Create Center"), "createCenter");
        bt2 = bind(new Button("Cancel"), "cancel");
        btLayout.addComponent(bt1);
        btLayout.addComponent(bt2);

        grid.addComponent(layout);
        //grid.setExpandRatio(layout, 1.0f);

        grid.addComponent(btLayout);
        grid.setComponentAlignment(btLayout, Alignment.BOTTOM_RIGHT);

        grid.setMargin(true);
        setContent(grid);
    }
}
