package com.tentelemed.archipel.site.infrastructure.web;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.infrastructure.web.BaseView;
import com.tentelemed.archipel.core.infrastructure.web.ModuleRoot;
import com.tentelemed.archipel.site.domain.event.SiteDomainEvent;
import com.tentelemed.archipel.site.infrastructure.model.LocationQ;
import com.tentelemed.archipel.site.infrastructure.model.RoomQ;
import com.tentelemed.archipel.site.infrastructure.model.SiteQ;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
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
@ModuleRoot(value = UiSites.NAME)
public class UiSites extends BaseView<UiSitesModel> {
    public static final String NAME = "sites";

    @Autowired UiSitesModel model;
    private ComboBox combo;

    @Override
    public UiSitesModel getModel() {
        return model;
    }

    @Override
    public void postConstruct() {
        setSizeFull();
        setHeight("100%");
        Panel panel = new Panel("Medical Centers administration");
        panel.setSizeFull();
        panel.setHeight("100%");
        setCompositionRoot(panel);

        VerticalLayout grid = new VerticalLayout();
        grid.setSizeFull();
        grid.setHeight("100%");
        panel.setContent(grid);

        // Selection centre
        grid.addComponent(createSelectCenterPanel());

        TabSheet tab = new TabSheet();

        grid.addComponent(tab);

        tab.addComponent(createMainInfoPanel());
        tab.addComponent(createAddInfoPanel());
        tab.addComponent(createServicesPanel());
        tab.addComponent(createRoomsPanel());
        tab.addComponent(createEquipmentsPanel());

        grid.setMargin(true);
        grid.setSpacing(true);

    }

    private com.vaadin.ui.Component createEquipmentsPanel() {
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setCaption("Equipments");
        return vlayout;
    }

    private com.vaadin.ui.Component createRoomsPanel() {
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setCaption("Rooms management");

         /* Create the table with a caption. */
        final Table table = new Table();

        BeanItemContainer<RoomQ> container = new BeanItemContainer<>(RoomQ.class);
        table.setContainerDataSource(container);

        /* Define the names and data types of columns.
         * The "default value" parameter is meaningless here. */
        container.addNestedContainerProperty("name");
        container.addNestedContainerProperty("medical");
        container.addNestedContainerProperty("nbBeds");
        container.addNestedContainerProperty("locationCode");

        // Use the login property as the item ID of the bean
        //container.setBeanIdProperty("entityId");

        // Headers
        table.setColumnHeader("name", "Name");
        table.setColumnHeader("medical", "Medical Room");
        table.setColumnHeader("nbBeds", "Beds");
        table.setColumnHeader("locationCode", "Location");

        // Have to set explicitly to hide the "equatorial" property
        table.setVisibleColumns(new String[]{"name", "medical", "nbBeds", "locationCode"});

        container.addAll(getModel().getRooms());

        table.setSelectable(true);
        table.setImmediate(true);

        // Handle selection change.
        table.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {
                RoomQ room = (RoomQ) table.getValue();
                model.setSelectedRoom(room);
                refreshUI();
            }
        });

        HorizontalLayout hlayout = new HorizontalLayout();
        VerticalLayout rightLayout = new VerticalLayout();
        rightLayout.setSizeFull();
        HorizontalLayout btLayout = new HorizontalLayout();
        btLayout.setSpacing(true);
        rightLayout.addComponent(btLayout);
        rightLayout.setComponentAlignment(btLayout, Alignment.BOTTOM_RIGHT);
        btLayout.addComponent(bind(new Button("Create Room"), "createRoom"));
        btLayout.addComponent(bind(new Button("Delete Room"), "deleteRoom"));
        hlayout.addComponent(table);
        hlayout.addComponent(rightLayout);
        vlayout.addComponent(hlayout);
        return vlayout;
    }

    private com.vaadin.ui.Component createServicesPanel() {
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setCaption("Medical Center Departments");

        TreeTable treeTable = new TreeTable();
        treeTable.removeAllItems();
        treeTable.setSizeFull();
        //Container.Hierarchical ds = treeTable.getContainerDataSource();
        treeTable.addContainerProperty("name", String.class, "--");
        treeTable.addContainerProperty("type", String.class, "?");
        treeTable.addContainerProperty("code", String.class, "..");
        treeTable.addContainerProperty("action", AbstractComponent.class, null);

        // Headers
        treeTable.setColumnHeader("type", "Type");
        treeTable.setColumnHeader("code", "Code");
        treeTable.setColumnHeader("name", "Name");
        treeTable.setColumnHeader("action", "Action");

        treeTable.setSelectable(true);

        if (model.getCurrentSite() != null) {
            for (LocationQ sector : model.getCurrentSite().getSectors()) {
                Item item = treeTable.addItem(sector);
                item.getItemProperty("name").setValue(sector.getName());
                item.getItemProperty("code").setValue(sector.getCode());
                item.getItemProperty("type").setValue("Sector " + sector.getType().name());
                item.getItemProperty("action").setValue(createActionPanel(sector));
                for (LocationQ service : sector.getChildren()) {
                    item = treeTable.addItem(service);
                    item.getItemProperty("name").setValue(service.getName());
                    item.getItemProperty("code").setValue(service.getCode());
                    item.getItemProperty("type").setValue("Service");
                    treeTable.setParent(service, sector);
                    for (LocationQ fu : service.getChildren()) {
                        item = treeTable.addItem(fu);
                        item.getItemProperty("name").setValue(fu.getName());
                        item.getItemProperty("code").setValue(fu.getCode());
                        item.getItemProperty("type").setValue("Functional Unit");
                        treeTable.setParent(fu, service);
                        for (LocationQ au : fu.getChildren()) {
                            item = treeTable.addItem(fu);
                            item.getItemProperty("name").setValue(au.getName());
                            item.getItemProperty("code").setValue(au.getCode());
                            item.getItemProperty("type").setValue("Activity Unit");
                            treeTable.setParent(au, fu);
                        }
                    }
                }
            }

            // Expand all nodes
            for (Object item : treeTable.getContainerDataSource().getItemIds().toArray())
                treeTable.setCollapsed(item, false);
        }
        treeTable.refreshRowCache();
        vlayout.addComponent(treeTable);
        return vlayout;
    }

    private AbstractComponent createActionPanel(final LocationQ loc) {
        if (loc == null) return null;
        if (loc.getType() == LocationQ.Type.SECTOR) {
            HorizontalLayout layout = new HorizontalLayout();
            layout.setSpacing(true);
            Button btAddService = new Button("Add Service");
            btAddService.addStyleName("small");
            layout.addComponent(btAddService);

            btAddService.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    model.createService(loc);
                }
            });
            return layout;
        }
        return null;
    }

    private AbstractComponent createAddInfoPanel() {
        //Panel panelAddInfo = new Panel("Additional info");
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setCaption("Additional info");
        //panelAddInfo.setContent(vlayout);
        FormLayout formLayout = new FormLayout();
        formLayout.addComponent(bind(new Label("Siret"), "currentSite.info.siret"));
        formLayout.addComponent(bind(new Label("Street"), "currentSite.info.address.street"));
        formLayout.addComponent(bind(new Label("Town"), "currentSite.info.address.town"));
        formLayout.addComponent(bind(new Label("Postal Code"), "currentSite.info.address.postalCode"));
        formLayout.addComponent(bind(new Label("Country"), "currentSite.info.address.countryIso"));
        formLayout.addComponent(bind(new Label("Phone"), "currentSite.info.phone"));
        formLayout.addComponent(bind(new Label("Fax"), "currentSite.info.fax"));
        formLayout.addComponent(bind(new Label("Director"), "currentSite.info.directorName"));
        formLayout.addComponent(bind(new Label("Bank"), "currentSite.info.bankCode"));
        formLayout.addComponent(bind(new Label("Emergencies ?"), "currentSite.info.emergenciesAvailable"));
        formLayout.addComponent(bind(new Label("Pharmacy ?"), "currentSite.info.pharmacyAvailable"));
        formLayout.addComponent(bind(new Label("Private rooms ?"), "currentSite.info.privateRoomAvailable"));

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
        formLayout.addComponent(bind(new Label("Type"), "currentSite.type"));
        formLayout.addComponent(bind(new Label("Name"), "currentSite.name"));
        formLayout.addComponent(bind(new Label("Ident"), "currentSite.ident"));
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
        for (SiteQ center : model.getSites()) {
            combo.addItem(center);
            combo.setItemCaption(center, center.getName());
        }
        combo.addValueChangeListener(getListener());
        combo.setValue(model.getCurrentSite());
    }

    Property.ValueChangeListener listener;

    private Property.ValueChangeListener getListener() {
        if (listener == null) {
            listener = new Property.ValueChangeListener() {
                @Override public void valueChange(Property.ValueChangeEvent event) {
                    model.setCurrentSite((SiteQ) combo.getValue());
                    refreshUI();
                }
            };
        }
        return listener;
    }

    @Override
    public void onDomainEventReceived(DomainEvent event) {
        if (event instanceof SiteDomainEvent) {
            initCombo();
            refreshUI();
        }
    }
}