package com.tentelemed.archipel.medicalcenter.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BasePopup;
import com.tentelemed.archipel.core.infrastructure.web.BaseView;
import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.core.infrastructure.web.ModuleRoot;
import com.tentelemed.archipel.medicalcenter.infrastructure.model.MedicalCenterQ;
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

        setModal(true);

        setWidth("250px");
        setHeight("300px");
        center();

        VerticalLayout grid = new VerticalLayout();

        FormLayout layout = new FormLayout();
        layout.addComponent(bind(new TextField("Type"), "medicalCenter.type"));
        layout.addComponent(bind(new TextField("Name"), "medicalCenter.name"));
        layout.addComponent(bind(new TextField("Ident"), "medicalCenter.ident"));
        layout.addComponent(bind(new Button("Create Center"), "createCenter"));
        layout.addComponent(bind(new Button("Cancel"), "cancel"));
        grid.addComponent(layout);

        setContent(grid);
    }
}
