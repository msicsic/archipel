package com.tentelemed.archipel.module.security.web;

import com.tentelemed.archipel.core.web.BasicView;
import com.tentelemed.archipel.core.web.ModuleRoot;
import com.tentelemed.archipel.module.security.domain.User;
import com.tentelemed.archipel.module.security.domain.UserId;
import com.tentelemed.archipel.module.security.event.domain.UserDTO;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

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

    private Table table;

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
        ComponentContainer formComponent = createFormComponent();
        hsplit.setSecondComponent(formComponent);
    }

    private ComponentContainer createFormComponent() {
        FormLayout layout = new FormLayout();
        layout.addComponent(bind(new TextField("Login"), "login"));
        layout.addComponent(bind(new TextField("First Name"), "firstName"));
        layout.addComponent(bind(new TextField("Last Name"), "lastName"));
//        layout.addComponent(bind(new TextField(), "selectedUser.firstName"));
//        layout.addComponent(bind(new TextField(), "selectedUser.lastName"));
        return layout;
    }

    private Table createTable() {
        /* Create the table with a caption. */
        table = new Table("This is my Table");

        BeanItemContainer<UserDTO> container = new BeanItemContainer<>(UserDTO.class);
        table.setContainerDataSource(container);

        /* Define the names and data types of columns.
         * The "default value" parameter is meaningless here. */
        container.addNestedContainerProperty("firstName");
        container.addNestedContainerProperty("lastName");
        container.addNestedContainerProperty("login");

        // Use the login property as the item ID of the bean
        //container.setBeanIdProperty("entityId");

        // Headers
        table.setColumnHeader("firstName", "First Name");
        table.setColumnHeader("lastName", "Last Name");
        table.setColumnHeader("login", "Login");

        // Have to set explicitly to hide the "equatorial" property
        table.setVisibleColumns(new String[]{"firstName", "lastName", "login"});

        container.addAll(getModel().getUsers());

        table.setSelectable(true);
        table.setImmediate(true);

        // Handle selection change.
        table.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {
                UserDTO user = (UserDTO) table.getValue();
                model.setSelectedUser(user);
                refreshUI();
            }
        });
        model.setSelectedUser(getModel().getUsers().get(0));

        return table;
    }

    @Override
    protected void onRefresh() {
        table.setValue(model.getSelectedUser());
    }

}
