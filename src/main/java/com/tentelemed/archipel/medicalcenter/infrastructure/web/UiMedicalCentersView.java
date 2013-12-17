package com.tentelemed.archipel.medicalcenter.infrastructure.web;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.infrastructure.web.BaseView;
import com.tentelemed.archipel.core.infrastructure.web.ModuleRoot;
import com.tentelemed.archipel.medicalcenter.domain.event.MedicalCenterDomainEvent;
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

        // Selection centre
        grid.addComponent(createSelectCenterPanel());

        TabSheet tab = new TabSheet();

        grid.addComponent(tab);

        tab.addComponent(createMainInfoPanel());
        tab.addComponent(createAddInfoPanel());
        tab.addComponent(createServicesPanel());
        tab.addComponent(createRoomsPanel());

        grid.setMargin(true);
        grid.setSpacing(true);

    }

    private com.vaadin.ui.Component createRoomsPanel() {
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setCaption("Rooms management");
        return vlayout;
    }

    private com.vaadin.ui.Component createServicesPanel() {
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setCaption("Medical Center Structure");

        Tree tree = new Tree();

        /*Division div = model.getCurrentCenter().getDivision();

        if (div == null) {
            div = model.createDefaultDivision();
        }
        tree.addItem(div.getSectors());
        for (Sector sector : div.getSectors()) {
            // TODO
        }

         */
        return vlayout;
    }

    private AbstractComponent createAddInfoPanel() {
        //Panel panelAddInfo = new Panel("Additional info");
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setCaption("Additional info");
        //panelAddInfo.setContent(vlayout);
        FormLayout formLayout = new FormLayout();
        formLayout.addComponent(bind(new Label("Siret"), "currentCenter.info.siret"));
        formLayout.addComponent(bind(new Label("Street"), "currentCenter.info.address.street"));
        formLayout.addComponent(bind(new Label("Town"), "currentCenter.info.address.town"));
        formLayout.addComponent(bind(new Label("Postal Code"), "currentCenter.info.address.postalCode"));
        formLayout.addComponent(bind(new Label("Country"), "currentCenter.info.address.country.isoCode"));
        formLayout.addComponent(bind(new Label("Phone"), "currentCenter.info.phone"));
        formLayout.addComponent(bind(new Label("Fax"), "currentCenter.info.fax"));
        formLayout.addComponent(bind(new Label("Director"), "currentCenter.info.directorName"));
        formLayout.addComponent(bind(new Label("Bank"), "currentCenter.info.bank.code"));
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
        Button btEdit = bind(new Button("edit"), "editAddInfo");
        btEdit.setStyleName("small");
        vlayout.addComponent(btEdit);

        //return panelAddInfo;
        return vlayout;
    }

    private AbstractComponent createMainInfoPanel() {
        //Panel panelInfo = new Panel("Main info");
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setCaption("Main Info");
        //panelInfo.setContent(vlayout);

        FormLayout formLayout = new FormLayout();
        formLayout.setSpacing(true);
        formLayout.addComponent(bind(new Label("Type"), "currentCenter.type"));
        formLayout.addComponent(bind(new Label("Name"), "currentCenter.name"));
        formLayout.addComponent(bind(new Label("Ident"), "currentCenter.ident"));
        vlayout.addComponent(formLayout);

        HorizontalLayout btLayout = new HorizontalLayout();
        Button bt1 = bind(new Button("edit"), "editMain");
        bt1.setStyleName("small");
        btLayout.addComponent(bt1);
        vlayout.addComponent(btLayout);

        //return panelInfo;
        return vlayout;
    }

    private AbstractComponent createSelectCenterPanel() {
        HorizontalLayout panelFilters = new HorizontalLayout();
        panelFilters.setSpacing(true);
        //panelFilters.setMargin(true);
        panelFilters.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        combo = new ComboBox("Select Center");
        combo.setTextInputAllowed(false);
        combo.setImmediate(true);
        initCombo();
        panelFilters.addComponent(combo);
        panelFilters.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
        Button btCreate = bind(new Button("create Center"), "createCenter");
        btCreate.setStyleName("default");
        panelFilters.addComponent(btCreate);
        Button btDelete = bind(new Button("delete Center"), "deleteCenter");
        btDelete.addStyleName("default");
        panelFilters.addComponent(btDelete);
        return panelFilters;
    }

    private void initCombo() {
        combo.removeValueChangeListener(getListener());
        combo.removeAllItems();
        for (MedicalCenterQ center : model.getMedicalCenters()) {
            combo.addItem(center);
            combo.setItemCaption(center, center.getName());
        }
        combo.addValueChangeListener(getListener());
        combo.setValue(model.getCurrentCenter());
    }

    Property.ValueChangeListener listener;

    private Property.ValueChangeListener getListener() {
        if (listener == null) {
            listener = new Property.ValueChangeListener() {
                @Override public void valueChange(Property.ValueChangeEvent event) {
                    model.setCurrentCenter((MedicalCenterQ) combo.getValue());
                    refreshUI();
                }
            };
        }
        return listener;
    }

    @Override
    public void onDomainEventReceived(DomainEvent event) {
        if (event instanceof MedicalCenterDomainEvent) {
            initCombo();
            refreshUI();
        }
    }
}
