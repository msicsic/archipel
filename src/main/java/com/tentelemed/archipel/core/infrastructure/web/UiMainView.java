package com.tentelemed.archipel.core.infrastructure.web;

import com.tentelemed.archipel.core.domain.model.Module;
import com.tentelemed.archipel.security.infrastructure.model.RoleQ;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 28/10/13
 * Time: 16:19
 */
@Component
@Scope("session")
@ModuleRoot(value = UiMainView.NAME, root = true)
public class UiMainView extends BaseView<MainViewModel> implements RootView {

    public static final String NAME = "main";

    Map<Module, MenuBar.MenuItem> mapMenu = new HashMap<>();
    VerticalLayout childLayout;

    @Autowired MainViewModel model;
    private Label labelUser;

    public void onDisplay() {
        // The view root layout
        VerticalLayout viewLayout = new VerticalLayout();
        viewLayout.setStyleName("black-bg");
        viewLayout.setSizeFull();
        viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
        setCompositionRoot(viewLayout);
        setStyleName("black-bg");
        // Create a menu bar
        MenuBar menubar = new MenuBar();
        menubar.setSizeFull();
        VerticalLayout imageLayout = new VerticalLayout();
        Image image = new Image(null, new ClassResource("/gemed.jpg"));
        imageLayout.setStyleName("black-bg");
        imageLayout.addComponent(image);
        imageLayout.setComponentAlignment(image, Alignment.MIDDLE_CENTER);

        //HorizontalLayout loginInfo = new HorizontalLayout();
        labelUser = new Label();
        labelUser.setSizeUndefined();
        labelUser.addStyleName("black-bg");
        labelUser.addStyleName("white-text");
        HorizontalLayout userLayout = new HorizontalLayout();
        userLayout.setWidth("100%");
        userLayout.setStyleName("black-bg");
        userLayout.addComponent(labelUser);
        userLayout.setComponentAlignment(labelUser, Alignment.MIDDLE_RIGHT);

        viewLayout.addComponent(imageLayout);
        viewLayout.addComponent(userLayout);
        viewLayout.addComponent(menubar);

        childLayout = new VerticalLayout();
        viewLayout.addComponent(childLayout);
        childLayout.setSizeFull();
        for (final Module module : model.getModules()) {
            MenuBar.MenuItem item = menubar.addItem(getName(module.getName()), null, new MenuBar.Command() {
                @Override
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    model.setSelectedModule(module);
                    refreshUI();
                }
            });
            item.setCheckable(true);
            mapMenu.put(module, item);
        }
        //menubar.addItem()
        MenuBar.MenuItem logoutMenu = menubar.addItem("Logout", null, null);
        logoutMenu.addItem("logout", null, new MenuBar.Command() {
            @Override public void menuSelected(MenuBar.MenuItem selectedItem) {
                model.logout();
            }
        });

    }

    private String getName(String key) {
        return msg.getMessage("RootMenu." + key);
    }

    @Override
    public void onRefresh() {
        for (Map.Entry<Module, MenuBar.MenuItem> entry : mapMenu.entrySet()) {
            entry.getValue().setEnabled(true);
            boolean showModule = model.isPermitted(entry.getKey().getName());
            entry.getValue().setVisible(showModule);
        }

        Module module = model.getSelectedModule();
        if (module != null) {
            MenuBar.MenuItem item = mapMenu.get(module);
            item.setEnabled(false);
        }
        labelUser.setValue(getModel().getUserInfo());
    }

    public void showView(IView view) {
        childLayout.removeAllComponents();
        childLayout.addComponent((AbstractComponent) view);
    }

    @Override
    public MainViewModel getModel() {
        return model;
    }
}

