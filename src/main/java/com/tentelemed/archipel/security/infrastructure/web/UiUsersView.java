package com.tentelemed.archipel.security.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BasicView;
import com.tentelemed.archipel.core.infrastructure.web.ModuleRoot;
import com.tentelemed.archipel.security.application.model.UserDTO;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
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
@ModuleRoot(value = UiUsersView.NAME)
public class UiUsersView extends BasicView<UiUsersViewModel> {

    public final static String NAME = "users";

    @Autowired UiUsersViewModel model;

    private Table table;
    private TextField firstName = new TextField("First Name :");
    private TextField lastName = new TextField("Last Name :");
    private TextField login = new TextField("Login :");
    private TextField email = new TextField("Email :");

    @Override
    protected UiUsersViewModel getModel() {
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

        HorizontalLayout panelButtons = new HorizontalLayout();
        panelButtons.setSpacing(true);
        panelButtons.addComponent(bind(new Button("Add"), "add"));
        panelButtons.addComponent(bind(new Button("Delete"), "delete"));
        panelButtons.addComponent(bind(new Button("Commit"), "commit"));
        panelButtons.addComponent(bind(new Button("Cancel"), "discard"));

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

    public String getLogin() {
        return getModel().getSelectedUser() == null ? "---" : getModel().getSelectedUser().getLogin();
    }

    private ComponentContainer createFormComponent() {
        FormLayout layout = new FormLayout();
        layout.setMargin(true);
        layout.addComponent(bind(firstName, "selectedUser.firstName"));
        layout.addComponent(bind(lastName, "selectedUser.lastName"));
        layout.addComponent(bind(login, "selectedUser.login"));
        layout.addComponent(bind(email, "selectedUser.email"));
        layout.addComponent(new Label("TOTO"));
        layout.addComponent(bind(new TextField("Full Name : "), "selectedUser.fullName"));
        layout.addComponent(bind(new DateField("Date of Birth : "), "selectedUser.dob"));
        layout.addComponent(bind(new Label("Full Name : "), "selectedUser.fullName"));
        //binder.bindMemberFields(this);
        //binder.setItemDataSource(new NestingBeanItem(model));
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

        return table;
    }

    @Override
    protected void onRefresh() {
        getBinder().discard();
        table.setValue(model.getSelectedUser());
        table.markAsDirtyRecursive();
    }

}
