package com.tentelemed.archipel.core.infrastructure.web;

import com.tentelemed.archipel.core.domain.model.Module;
import com.tentelemed.archipel.core.application.service.CoreService;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 25/10/13
 * Time: 14:18
 */
@Component
@Scope("session")
@Title("Login frame")
public class MyUI extends UI {

    @Autowired
    ApplicationContext appContext;

    @Autowired
    CoreService coreService;

    AbstractComponent mainView;
    AbstractComponent loginView;

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


        showView(loginView);
    }

    public void showView(String moduleId) {
        // recuperation du module
        Module module = coreService.getModule(moduleId);
        if (module.isRoot()) {
            showView(mainView);
        } else {
            AbstractComponent view = appContext.getBean(module.getViewClass());
            ((RootView)mainView).showView(view);
        }
    }

    private void showView(AbstractComponent view) {
        this.setContent(view);
    }


}
