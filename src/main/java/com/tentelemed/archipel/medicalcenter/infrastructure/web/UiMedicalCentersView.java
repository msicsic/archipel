package com.tentelemed.archipel.medicalcenter.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BaseView;
import com.tentelemed.archipel.core.infrastructure.web.ModuleRoot;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenter;
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
@ModuleRoot(value = UiMedicalCentersView.NAME)
public class UiMedicalCentersView extends BaseView<UiMedicalCentersViewModel> {
    public static final String NAME = "medicalCenters";

    @Autowired UiMedicalCentersViewModel model;

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
        ComboBox combo = new ComboBox("Select Center");
        combo.setTextInputAllowed(false);
        combo.setImmediate(true);
        for (MedicalCenterQ center : model.getMedicalCenters()) {
            combo.addItem(center);
        }
        panelFilters.addComponent(combo);
        panelFilters.addComponent(bind(new Button("create Center"),"createCenter"));
        panelFilters.addComponent(bind(new Button("delete Center"),"deleteCenter"));
        grid.addComponent(panelFilters);

        Panel panelInfo = new Panel("Main info");
        FormLayout layout = new FormLayout();
        layout.addComponent(bind(new TextField("Type"), "currentCenter.type"));
        layout.addComponent(bind(new TextField("Name"), "currentCenter.name"));
        layout.addComponent(bind(new TextField("Ident"), "currentCenter.ident"));
        layout.addComponent(bind(new Button("edit"), "editMain"));
        panelInfo.setContent(layout);
        grid.addComponent(panelInfo);

        Panel panelAddInfo = new Panel("Additional info");
        FormLayout layoutAdd = new FormLayout();
        layoutAdd.addComponent(bind(new TextField("Siret"), "currentCenter.siret"));
        layoutAdd.addComponent(bind(new TextField("Street"), "currentCenter.street"));
        layoutAdd.addComponent(bind(new TextField("Town"), "currentCenter.town"));
        layoutAdd.addComponent(bind(new TextField("Postal Code"), "currentCenter.postalCode"));
        layoutAdd.addComponent(bind(new TextField("Country"), "currentCenter.countryISO"));
        layoutAdd.addComponent(bind(new TextField("Phone"), "currentCenter.phone"));
        layoutAdd.addComponent(bind(new TextField("Fax"), "currentCenter.fax"));
        layoutAdd.addComponent(bind(new TextField("Director"), "currentCenter.directorName"));
        layoutAdd.addComponent(bind(new TextField("Bank"), "currentCenter.bankCode"));
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
}
