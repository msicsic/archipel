package com.tentelemed.archipel.core.web;

import com.google.common.eventbus.EventBus;
import com.tentelemed.archipel.core.domain.Module;
import com.tentelemed.archipel.module.security.service.UserService;
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
@ModuleRoot(value = MainView.NAME, root = true)
public class MainView extends BasicView<MainViewModel> implements RootView {

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
            MenuBar.MenuItem item = menubar.addItem(module.getName(), null, new MenuBar.Command() {
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

    @Override
    protected void onRefresh() {
        for (Map.Entry<Module, MenuBar.MenuItem> entry : mapMenu.entrySet()) {
            entry.getValue().setEnabled(true);
            entry.getValue().setChecked(false);
            entry.getValue().setText(entry.getKey().getName());
        }

        Module module = model.getSelectedModule();
        if (module != null) {
            MenuBar.MenuItem item = mapMenu.get(module);
            item.setChecked(true);
            item.setText("[" + module.getName() + "]");
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

