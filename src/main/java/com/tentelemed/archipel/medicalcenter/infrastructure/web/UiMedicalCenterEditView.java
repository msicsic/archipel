package com.tentelemed.archipel.medicalcenter.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BasePopup;
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
public class UiMedicalCenterEditView extends BasePopup<UiMedicalCenterEditViewModel> {

    @Autowired UiMedicalCenterEditViewModel model;

    @Override
    public UiMedicalCenterEditViewModel getModel() {
        return model;
    }

    @Override
    public void postConstruct() {
        setCaption("Edit Center Info");

        setModal(true);
        setResizable(false);

        setWidth("300px");
        setHeight("500px");
        center();

        setContent(createAddInfoPanel());
    }

    private AbstractComponent createAddInfoPanel() {

        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setCaption("Additional info");

        FormLayout formLayout = new FormLayout();
        formLayout.addComponent(bind(new TextField("Siret"), "cmd.siret"));
        formLayout.addComponent(bind(new TextField("Street"), "cmd.street"));
        formLayout.addComponent(bind(new TextField("Town"), "cmd.town"));
        formLayout.addComponent(bind(new TextField("Postal Code"), "cmd.postalCode"));
        formLayout.addComponent(bind(new TextField("Country"), "cmd.country.isoCode"));
        formLayout.addComponent(bind(new TextField("Phone"), "cmd.phone"));
        formLayout.addComponent(bind(new TextField("Fax"), "cmd.fax"));
        formLayout.addComponent(bind(new TextField("Director"), "cmd.directorName"));
        formLayout.addComponent(bind(new TextField("Bank"), "cmd.bank.code"));
        OptionGroup groupEmergencies = new OptionGroup("Emergencies ? ");
        groupEmergencies.addItem("yes");
        groupEmergencies.addItem("no");
        formLayout.addComponent(groupEmergencies);
        OptionGroup groupDrugStore = new OptionGroup("Pharmacy ? ");
        groupDrugStore.addItem("yes");
        groupDrugStore.addItem("no");
        formLayout.addComponent(groupDrugStore);
        OptionGroup groupPrivateRooms = new OptionGroup("Private rooms ? ");
        groupPrivateRooms.addItem("yes");
        groupPrivateRooms.addItem("no");
        formLayout.addComponent(groupPrivateRooms);

        vlayout.addComponent(formLayout);
        return vlayout;
    }

    public void setCenter(MedicalCenterQ center) {
        model.setCenter(center);
    }
}
