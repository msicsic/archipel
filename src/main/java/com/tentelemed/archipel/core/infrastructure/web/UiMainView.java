package com.tentelemed.archipel.core.infrastructure.web;

import com.tentelemed.archipel.core.domain.model.Module;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
public class UiMainView extends BasicView<MainViewModel> implements RootView {

    public static final String NAME = "main";

    Map<Module, MenuBar.MenuItem> mapMenu = new HashMap<>();
    VerticalLayout childLayout;

    @Autowired MainViewModel model;

    @PostConstruct
    public void postConstruct() {

        // The view root layout
        VerticalLayout viewLayout = new VerticalLayout();
        viewLayout.setSizeFull();
        viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
        setCompositionRoot(viewLayout);

        // Create a menu bar
        MenuBar menubar = new MenuBar();
        menubar.setSizeFull();
        viewLayout.addComponent(menubar);

        childLayout = new VerticalLayout();
        viewLayout.addComponent(childLayout);

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
        return msg.getMessage("RootMenu."+key);
    }

    @Override
    protected void onRefresh() {
        for (Map.Entry<Module, MenuBar.MenuItem> entry : mapMenu.entrySet()) {
            entry.getValue().setEnabled(true);
        }

        Module module = model.getSelectedModule();
        if (module != null) {
            MenuBar.MenuItem item = mapMenu.get(module);
            item.setEnabled(false);
        }
    }

    public void showView(AbstractComponent view) {
        childLayout.removeAllComponents();
        childLayout.addComponent(view);
    }

    @Override
    protected MainViewModel getModel() {
        return model;
    }
}

