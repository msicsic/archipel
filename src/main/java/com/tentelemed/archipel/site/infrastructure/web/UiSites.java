package com.tentelemed.archipel.site.infrastructure.web;

import com.tentelemed.archipel.core.domain.pub.DomainEvent;
import com.tentelemed.archipel.core.infrastructure.web.BaseView;
import com.tentelemed.archipel.core.infrastructure.web.ModuleRoot;
import com.tentelemed.archipel.site.domain.pub.*;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private VerticalLayout sectorsPanel;
    private BeanItemContainer<Bed> containerBeds;
    private Table tableBeds;

    @Override
    public UiSitesModel getModel() {
        return model;
    }

    @Override
    public void onDisplay() {
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
        tab.addComponent(createSectorsPanel());
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

         /* Create the table with a caption. */
        final Table tableRooms = new Table();

        BeanItemContainer<RoomQ> container = new BeanItemContainer<>(RoomQ.class);
        tableRooms.setContainerDataSource(container);

        container.addNestedContainerProperty("name");
        container.addNestedContainerProperty("medical");
        container.addNestedContainerProperty("nbBeds");
        container.addNestedContainerProperty("locationPath");

        tableRooms.setColumnHeader("name", "Name");
        tableRooms.setColumnHeader("medical", "Medical Room");
        tableRooms.setColumnHeader("nbBeds", "Beds");
        tableRooms.setColumnHeader("locationCode", "Location");

        tableRooms.setVisibleColumns(new String[]{"name", "medical", "nbBeds", "locationPath"});
        container.addAll(getModel().getRooms());
        tableRooms.setSelectable(true);
        tableRooms.setImmediate(true);

        // Handle selection change.
        tableRooms.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {
                if (isRefreshing()) return;
                RoomQ room = (RoomQ) tableRooms.getValue();
                model.setSelectedRoom(room);
                refreshUI();
            }
        });
        tableRooms.setSizeFull();

         /* Create the table with a caption. */
        tableBeds = new Table();
        containerBeds = new BeanItemContainer<>(Bed.class);
        tableBeds.setContainerDataSource(containerBeds);
        containerBeds.addNestedContainerProperty("name");
        tableBeds.setColumnHeader("name", "Name");
        tableBeds.setVisibleColumns(new String[]{"name"});
        tableBeds.setSelectable(true);
        tableBeds.setImmediate(true);
        tableBeds.setSizeFull();
        updateBeds();

        // Handle selection change.
        tableBeds.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {
                if (isRefreshing()) return;
                Bed bed = (Bed) tableBeds.getValue();
                model.setSelectedBed(bed);
                refreshUI();
            }
        });

        listen("selectedRoom", new Runnable() {
            @Override public void run() {
                updateBeds();
            }
        });

        HorizontalLayout btLeft = new HorizontalLayout();
        btLeft.setSpacing(true);
        btLeft.addComponent(bind(new Button("Create Room"), "createRoom"));
        btLeft.addComponent(bind(new Button("Delete Room"), "deleteRoom"));

        VerticalLayout left = new VerticalLayout();
        left.setSizeFull();
        left.setWidth("100%");
        left.addComponent(tableRooms);
        left.addComponent(btLeft);
        left.setComponentAlignment(btLeft, Alignment.BOTTOM_RIGHT);

        HorizontalLayout btRight = new HorizontalLayout();
        btRight.setSpacing(true);
        btRight.addComponent(bind(new Button("Create Bed"), "createBed"));
        btRight.addComponent(bind(new Button("Delete Bed"), "deleteBed"));

        VerticalLayout right = new VerticalLayout();
        right.setSizeFull();
        right.addComponent(tableBeds);
        right.addComponent(btRight);
        right.setComponentAlignment(btRight, Alignment.BOTTOM_RIGHT);

        HorizontalLayout main = new HorizontalLayout();
        main.setSizeFull();
        main.setSpacing(true);
        main.addComponent(left);
        main.addComponent(right);

        main.setCaption("Rooms management");
        return main;
    }

    private com.vaadin.ui.Component createSectorsPanel() {
        if (sectorsPanel == null) {
            sectorsPanel = new VerticalLayout();
            sectorsPanel.setCaption("Medical Center Departments");
        }
        sectorsPanel.removeAllComponents();

        TreeTable treeTable = new TreeTable();
        treeTable.removeAllItems();
        treeTable.setSizeFull();
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
            List<LocationQ> sectors = new ArrayList<>(model.getCurrentSite().getSectors());
            Collections.sort(sectors);
            for (LocationQ sector : sectors) {
                Item item = treeTable.addItem(sector);
                item.getItemProperty("name").setValue(sector.getName());
                item.getItemProperty("code").setValue(sector.getCode());
                item.getItemProperty("type").setValue("Sector (" + sector.getSectorType().name() + ")");
                item.getItemProperty("action").setValue(createActionPanel(sector));
                for (LocationQ service : sector.getChildren()) {
                    item = treeTable.addItem(service);
                    item.getItemProperty("name").setValue(service.getName());
                    item.getItemProperty("code").setValue(service.getCode());
                    item.getItemProperty("type").setValue("Service");
                    item.getItemProperty("action").setValue(createActionPanel(service));
                    treeTable.setParent(service, sector);
                    for (LocationQ fu : service.getChildren()) {
                        item = treeTable.addItem(fu);
                        item.getItemProperty("name").setValue(fu.getName());
                        item.getItemProperty("code").setValue(fu.getCode());
                        item.getItemProperty("type").setValue("Functional Unit");
                        item.getItemProperty("action").setValue(createActionPanel(fu));
                        treeTable.setParent(fu, service);
                        for (LocationQ au : fu.getChildren()) {
                            item = treeTable.addItem(au);
                            item.getItemProperty("name").setValue(au.getName());
                            item.getItemProperty("code").setValue(au.getCode());
                            item.getItemProperty("type").setValue("Activity Unit");
                            item.getItemProperty("action").setValue(createActionPanel(au));
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
        sectorsPanel.addComponent(treeTable);
        return sectorsPanel;
    }

    private AbstractComponent createActionPanel(final LocationQ loc) {
        if (loc == null) return null;
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        Button bt1 = null;
        Button bt2 = null;
        if (loc.getType() == LocationQ.Type.SECTOR) {
            bt1 = new Button("Add Service");
            bt1.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    model.createService(loc);
                }
            });

            if (loc.getSectorType() == SectorType.MED && model.getCurrentSite().getRemainingSectorTypes().size() > 0) {
                bt2 = new Button("Add Sector");
                bt2.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        model.createSector();
                    }
                });
            } else if (loc.getSectorType() != SectorType.MED) {
                bt2 = new Button("Delete Sector");
                bt2.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        model.deleteSector(loc);
                    }
                });
            }
        } else if (loc.getType() == LocationQ.Type.SERVICE) {
            bt1 = new Button("Delete Service");
            bt1.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    model.deleteService(loc);
                }
            });
            bt2 = new Button("Add F-Unit");
            bt2.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    model.createFunctionalUnit(loc);
                }
            });

        } else if (loc.getType() == LocationQ.Type.FU) {
            bt1 = new Button("Delete F-Unit");
            bt1.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    model.deleteFunctionalUnit(loc);
                }
            });
            bt2 = new Button("Add A-Unit");
            bt2.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    model.createActivityUnit(loc);
                }
            });

        } else if (loc.getType() == LocationQ.Type.AU) {
            bt1 = new Button("Delete A-Unit");
            bt1.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    model.deleteActivityUnit(loc);
                }
            });
        }

        if (bt1 != null) {
            bt1.addStyleName("small");
            layout.addComponent(bt1);
        }
        if (bt2 != null) {
            bt2.addStyleName("small");
            layout.addComponent(bt2);
        }
        return layout;
    }

    private AbstractComponent createAddInfoPanel() {
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setCaption("Additional info");
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

        return vlayout;
    }

    private AbstractComponent createMainInfoPanel() {
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setCaption("Main Info");

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

        return vlayout;
    }

    private AbstractComponent createSelectCenterPanel() {
        HorizontalLayout panelFilters = new HorizontalLayout();
        panelFilters.setSpacing(true);
        panelFilters.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        combo = new ComboBox("Select Center");
        combo.setTextInputAllowed(false);
        combo.setImmediate(true);
        initCombo();
        panelFilters.addComponent(combo);
        panelFilters.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
        Button btCreate = bind(new Button("create Center"), "createCenter");
        btCreate.addStyleName("default");
        btCreate.setVisible(isPermitted("create"));
        panelFilters.addComponent(btCreate);
        Button btDelete = bind(new Button("delete Center"), "deleteCenter");
        btDelete.addStyleName("default");
        btDelete.setVisible(isPermitted("delete"));
        panelFilters.addComponent(btDelete);
        return panelFilters;
    }

    private void refreshComboValues() {
        combo.removeAllItems();
        for (SiteQ center : model.getSites()) {
            combo.addItem(center);
            combo.setItemCaption(center, center.getName());
        }
        combo.setValue(model.getCurrentSite());
    }

    private void initCombo() {
        combo.addValueChangeListener(new Property.ValueChangeListener() {
            @Override public void valueChange(Property.ValueChangeEvent event) {
                if (isRefreshing()) return;
                model.setCurrentSite((SiteQ) combo.getValue());
                refreshUI();
            }
        });
        listen("sites", new Runnable() {
            @Override public void run() {
                refreshComboValues();
            }
        });
        listen("currentSite", new Runnable() {
            @Override public void run() {
                createSectorsPanel();
            }
        });
    }

    private void updateBeds() {
        containerBeds.removeAllItems();
        containerBeds.addAll(getModel().getBeds());
        tableBeds.setValue(getModel().getSelectedBed());
    }

    @Override
    public void onDomainEventReceived(DomainEvent event) {
        if (event instanceof EvtSiteDomainEvent) {
            refreshUI();
        }
    }

}
