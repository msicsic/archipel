package com.tentelemed.archipel.medicalcenter.infrastructure.web;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.infrastructure.web.BaseView;
import com.tentelemed.archipel.core.infrastructure.web.ModuleRoot;
import com.tentelemed.archipel.medicalcenter.domain.event.MedicalCenterRegistered;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenter;
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
@ModuleRoot(value = UiMedicalCentersView.NAME)
public class UiMedicalCentersView extends BaseView<UiMedicalCentersViewModel> {
    public static final String NAME = "medicalCenters";

    @Autowired UiMedicalCentersViewModel model;
    private ComboBox combo;

    @Override
    public UiMedicalCentersViewModel getModel() {
        return model;
    }

    @Override
    public void postConstruct() {
        setSizeFull();

        Panel panel = new Panel("Medical Centers administration");
        setCompositionRoot(panel);

        VerticalLayout grid = new VerticalLayout();
        panel.setContent(grid);

        // Filters
        HorizontalLayout panelFilters = new HorizontalLayout();
        panelFilters.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        combo = new ComboBox("Select Center");
        combo.setTextInputAllowed(false);
        combo.setImmediate(true);
        initCombo();
        combo.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                model.setCurrentCenter((MedicalCenterQ) combo.getValue());
                refreshUI();
            }
        });
        panelFilters.addComponent(combo);
        panelFilters.addComponent(bind(new Button("create Center"),"createCenter"));
        panelFilters.addComponent(bind(new Button("delete Center"),"deleteCenter"));
        grid.addComponent(panelFilters);

        Panel panelInfo = new Panel("Main info");
        FormLayout layout = new FormLayout();
        layout.addComponent(bind(new Label("Type"), "currentCenter.type"));
        layout.addComponent(bind(new Label("Name"), "currentCenter.name"));
        layout.addComponent(bind(new Label("Ident"), "currentCenter.ident"));
        layout.addComponent(bind(new Button("edit"), "editMain"));
        panelInfo.setContent(layout);
        grid.addComponent(panelInfo);

        Panel panelAddInfo = new Panel("Additional info");
        FormLayout layoutAdd = new FormLayout();
        layoutAdd.addComponent(bind(new Label("Siret"), "currentCenter.siret"));
        layoutAdd.addComponent(bind(new Label("Street"), "currentCenter.street"));
        layoutAdd.addComponent(bind(new Label("Town"), "currentCenter.town"));
        layoutAdd.addComponent(bind(new Label("Postal Code"), "currentCenter.postalCode"));
        layoutAdd.addComponent(bind(new Label("Country"), "currentCenter.countryISO"));
        layoutAdd.addComponent(bind(new Label("Phone"), "currentCenter.phone"));
        layoutAdd.addComponent(bind(new Label("Fax"), "currentCenter.fax"));
        layoutAdd.addComponent(bind(new Label("Director"), "currentCenter.directorName"));
        layoutAdd.addComponent(bind(new Label("Bank"), "currentCenter.bankCode"));
        OptionGroup groupEmergencies = new OptionGroup("Emergencies ? ");
        groupEmergencies.addItem("yes");
        groupEmergencies.addItem("no");
        layoutAdd.addComponent(groupEmergencies);
        OptionGroup groupDrugStore = new OptionGroup("Pharmacy ? ");
        groupDrugStore.addItem("yes");
        groupDrugStore.addItem("no");
        layoutAdd.addComponent(groupDrugStore);
        OptionGroup groupPrivateRooms = new OptionGroup("Private rooms ? ");
        groupPrivateRooms.addItem("yes");
        groupPrivateRooms.addItem("no");
        layoutAdd.addComponent(groupPrivateRooms);
        layoutAdd.addComponent(bind(new Button("edit"), "editAddInfo"));

        panelAddInfo.setContent(layoutAdd);
        grid.addComponent(panelAddInfo);

    }

    private void initCombo() {
        combo.removeAllItems();
        for (MedicalCenterQ center : model.getMedicalCenters()) {
            combo.addItem(center);
            combo.setItemCaption(center, center.getName());
        }
    }

    @Override
    protected void onDomainEventReceived(DomainEvent event) {
        if (event instanceof MedicalCenterRegistered) {
            initCombo();
            combo.setValue(model.getCurrentCenter());
            refreshUI();
        }
    }
}
