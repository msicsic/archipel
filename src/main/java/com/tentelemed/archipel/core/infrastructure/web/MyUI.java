package com.tentelemed.archipel.core.infrastructure.web;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tentelemed.archipel.core.application.event.LoginEvent;
import com.tentelemed.archipel.core.application.service.CoreService;
import com.tentelemed.archipel.core.domain.model.Module;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 25/10/13
 * Time: 14:18
 */
@Component
@Scope("session")
@Title("Login frame")
@Theme("mytheme")
public class MyUI extends UI {

    @Autowired
    ApplicationContext appContext;

    @Autowired
    CoreService coreService;

    @Autowired
    LocalDispatcher dispatcher;

    @Autowired
    @Qualifier("localBus")
    EventBus localBus;

    @Autowired
    EventBus eventBus;

    IView mainView;
    IView loginView;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setSizeFull();

        // instanciation des vues de login et principales
        for (Module module : coreService.findAllModules()) {
            if (module.isRoot()) {
                mainView = appContext.getBean(module.getViewClass());
            } else if (module.isLogin()) {
                loginView = appContext.getBean(module.getViewClass());
            }
        }

        // error handler
        // ...

        showView(loginView);
    }

    @PostConstruct
    public void _onPostConstruct() {
        eventBus.register(dispatcher);
        localBus.register(this);
    }

    @PreDestroy
    public void _onPreDestroy() {
        eventBus.unregister(dispatcher);
        UI.getCurrent().getSession().close();
        UI.getCurrent().getPage().setLocation("/");
    }

    IView currentView;

    public void showView(String moduleId) {
        // recuperation du module
        Module module = coreService.getModule(moduleId);
        if (module.isRoot()) {
            mainView.refreshUI();
            showView(mainView);
        } else {
            IView view = appContext.getBean(module.getViewClass());
            RootView rv = (RootView) mainView;
            if (currentView != null) {
                currentView.onClose();
            }
            view.onDisplay();
            view.refreshUI();
            currentView = view;
            ((RootView) mainView).showView(view);
        }
    }

    private void showView(IView view) {
        this.setContent((AbstractComponent) view);
    }

    @Subscribe
    public void handleViewEvent(NavigationEvent event) {
        ((MyUI) UI.getCurrent()).showView(event.getModuleId());
    }

    @Subscribe
    public void handleViewEvent(LoginEvent event) {
        ((MyUI) UI.getCurrent()).showView(UiMainView.NAME);
    }

}
