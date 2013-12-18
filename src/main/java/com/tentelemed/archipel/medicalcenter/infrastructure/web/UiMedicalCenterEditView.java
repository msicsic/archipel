package com.tentelemed.archipel.medicalcenter.infrastructure.web;

import com.tentelemed.archipel.core.domain.model.Country;
import com.tentelemed.archipel.core.infrastructure.web.BasePopup;
import com.tentelemed.archipel.medicalcenter.domain.model.Bank;
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
       // setResizable(false);

        setWidth("300px");
        setHeight("600px");
        center();

        setContent(createAddInfoPanel());
    }

    private AbstractComponent createAddInfoPanel() {

        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setCaption("Additional info");
        vlayout.setMargin(true);

        FormLayout formLayout = new FormLayout();
        formLayout.addComponent(bind(new TextField("Siret"), "cmd.siret"));
        formLayout.addComponent(bind(new TextField("Street"), "cmd.street"));
        formLayout.addComponent(bind(new TextField("Town"), "cmd.town"));
        formLayout.addComponent(bind(new TextField("Postal Code"), "cmd.postalCode"));
        final ComboBox cb = new ComboBox("Country");
        for (Country country : model.getCountries()) {
            cb.addItem(country);
            cb.setItemCaption(country, country.getName());
        }
        cb.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Country country = (Country) cb.getValue();
                model.getCmd().countryIso = country.getIsoCode();
            }
        });
        formLayout.addComponent(cb);
        formLayout.addComponent(bind(new TextField("Phone"), "cmd.phone"));
        formLayout.addComponent(bind(new TextField("Fax"), "cmd.fax"));
        formLayout.addComponent(bind(new TextField("Director"), "cmd.directorName"));
        final ComboBox cbBank = new ComboBox("Banks");
        for (Bank bank : model.getBanks()) {
            cbBank.addItem(bank);
            cbBank.setItemCaption(bank, bank.getBankName());
        }
        cbBank.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Bank bank = (Bank) cbBank.getValue();
                model.getCmd().bankCode = bank.getCode();
            }
        });
        formLayout.addComponent(cbBank);

        OptionGroup groupEmergencies = new OptionGroup("Emergencies ? ");
        groupEmergencies.addStyleName("display: inline-block");
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

        HorizontalLayout btLayout = new HorizontalLayout();
        btLayout.setSpacing(true);
        Button bt1 = bind(new Button("Confirm"), "confirm");
        Button bt2 = bind(new Button("Cancel"), "cancel");
        btLayout.addComponent(bt1);
        btLayout.addComponent(bt2);

        vlayout.setHeight("100%");
        vlayout.addComponent(formLayout);
        vlayout.addComponent(btLayout);
        vlayout.setComponentAlignment(btLayout, Alignment.BOTTOM_RIGHT);

        return vlayout;
    }

    public void setCenter(MedicalCenterQ center) {
        model.setCenter(center);
    }
}
