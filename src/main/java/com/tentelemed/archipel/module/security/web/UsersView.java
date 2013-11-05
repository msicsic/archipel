package com.tentelemed.archipel.module.security.web;

import com.tentelemed.archipel.core.web.BasicView;
import com.tentelemed.archipel.core.web.ModuleRoot;
import com.tentelemed.archipel.module.security.domain.User;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 28/10/13
 * Time: 14:13
 */
@Component
@Scope("prototype")
@ModuleRoot(value = UsersView.NAME)
public class UsersView extends BasicView<UsersViewModel> {

    public final static String NAME = "users";

    @Autowired UsersViewModel model;

    @Override
    protected UsersViewModel getModel() {
        return model;
    }

    @PostConstruct
    public void postConstruct() {

        setSizeFull();

        // Have a panel to put stuff in
        Panel panel = new Panel("Users administration");
        setCompositionRoot(panel);

        VerticalLayout grid = new VerticalLayout();
        panel.setContent(grid);

        // Filters...
        HorizontalLayout panelFilters = new HorizontalLayout();
        panelFilters.addComponent(new Label("Filters..."));

        // Buttons...
        Button btAdd = new Button("Add");
        Button btDelete = new Button("Delete");
        Button btEdit = new Button("Edit");
        bind(btAdd, "add");
        bind(btDelete, "delete");
        bind(btEdit, "edit");

        HorizontalLayout panelButtons = new HorizontalLayout();
        panelButtons.setSpacing(true);
        panelButtons.addComponent(btAdd);
        panelButtons.addComponent(btDelete);
        panelButtons.addComponent(btEdit);

        // Have a horizontal split panel as its content
        HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
        grid.addComponent(panelFilters);
        grid.addComponent(hsplit);
        grid.addComponent(panelButtons);
        grid.setComponentAlignment(panelButtons, Alignment.MIDDLE_RIGHT);

        Table table = createTable();
        table.setSizeFull();

        // Put a component in the left panel
        hsplit.setFirstComponent(table);

        // Put a vertical split panel in the right panel
        Panel formComponent = createFormComponent();
        hsplit.setSecondComponent(formComponent);
    }

    private Panel createFormComponent() {
        return new Panel();
    }

    private Table createTable() {
        /* Create the table with a caption. */
        final Table table = new Table("This is my Table");

        /* Define the names and data types of columns.
         * The "default value" parameter is meaningless here. */
        table.addContainerProperty("First Name", String.class,  null);
        table.addContainerProperty("Last Name",  String.class,  null);
        table.addContainerProperty("Year",       Integer.class, null);

        /* Add a few items in the table. */
        table.addItem(new Object[] {
                "Nicolaus","Copernicus",new Integer(1473)}, new Integer(1));
        table.addItem(new Object[] {
                "Tycho",   "Brahe",     new Integer(1546)}, new Integer(2));
        table.addItem(new Object[] {
                "Giordano","Bruno",     new Integer(1548)}, new Integer(3));
        table.addItem(new Object[] {
                "Galileo", "Galilei",   new Integer(1564)}, new Integer(4));
        table.addItem(new Object[] {
                "Johannes","Kepler",    new Integer(1571)}, new Integer(5));
        table.addItem(new Object[] {
                "Isaac",   "Newton",    new Integer(1643)}, new Integer(6));

        // Allow selecting items from the table.
        table.setSelectable(true);

        // Send changes in selection immediately to server.
        table.setImmediate(true);

        // Shows feedback from selection.
        final Label current = new Label("Selected: -");

        // Handle selection change.
        table.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {
                User user = (User) table.getValue();
                model.setSelectedUser(user);
            }
        });

        return table;
    }
}
