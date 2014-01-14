package com.tentelemed.archipel.site.infrastructure.web;

import com.tentelemed.archipel.core.domain.model.Country;
import com.tentelemed.archipel.core.infrastructure.web.BasePopup;
import com.tentelemed.archipel.site.domain.pub.Bank;
import com.tentelemed.archipel.site.infrastructure.model.SiteQ;
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
public class UiSiteEdit extends BasePopup<UiSiteEditModel> {

    @Autowired UiSiteEditModel model;

    @Override
    public UiSiteEditModel getModel() {
        return model;
    }

    @Override
    public void postConstruct() {

    }

    @Override
    public void onDisplay() {
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
            cb.addItem(country.getIsoCode());
            cb.setItemCaption(country.getIsoCode(), country.getName());
        }
        cb.setValue(model.getCmd().countryIso);
        cb.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                String countryIso = (String) cb.getValue();
                model.getCmd().countryIso = countryIso;
            }
        });
        formLayout.addComponent(cb);
        formLayout.addComponent(bind(new TextField("Phone"), "cmd.phone"));
        formLayout.addComponent(bind(new TextField("Fax"), "cmd.fax"));
        formLayout.addComponent(bind(new TextField("Director"), "cmd.directorName"));
        final ComboBox cbBank = new ComboBox("Banks");
        for (Bank bank : model.getBanks()) {
            cbBank.addItem(bank.getCode());
            cbBank.setItemCaption(bank.getCode(), bank.getBankName());
        }
        cbBank.setValue(model.getCmd().bankCode);
        cbBank.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                String bankCode = (String) cbBank.getValue();
                model.getCmd().bankCode = bankCode;
            }
        });
        formLayout.addComponent(cbBank);

        OptionGroup groupEmergencies = new OptionGroup("Emergencies ? ");
        groupEmergencies.addStyleName("display: inline-block");
        groupEmergencies.addItem(Boolean.TRUE);
        groupEmergencies.setItemCaption(Boolean.TRUE, "Yes");
        groupEmergencies.addItem(Boolean.FALSE);
        groupEmergencies.setItemCaption(Boolean.FALSE, "No");
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

    public void setCenter(SiteQ center) {
        model.setCenter(center);
    }
}
