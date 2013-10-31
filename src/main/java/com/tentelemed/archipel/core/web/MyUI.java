package com.tentelemed.archipel.core.web;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tentelemed.archipel.module.security.event.SecLoginEvent;
import com.tentelemed.archipel.module.security.event.SecLogoutEvent;
import com.tentelemed.archipel.module.security.web.LoginView;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import ru.xpoft.vaadin.VaadinView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    EventBus eventBus;

    @Autowired
    ApplicationContext appContext;

    Map<String, Class> mapViews = new HashMap<>();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setSizeFull();
        eventBus.register(this);

        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(VaadinView.class));
        Set<BeanDefinition> list = scanner.findCandidateComponents("com.tentelemed.archipel");
        for (BeanDefinition def : list) {
            try {
                Class c = Class.forName(def.getBeanClassName());
                VaadinView vv = (VaadinView) c.getAnnotation(VaadinView.class);
                String name = vv.value();
                mapViews.put(name, c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        showView(LoginView.NAME);
    }

    @Subscribe
    public void handleViewEvent(NavigationEvent event) {
        showView(event.getViewId());
    }

    @Subscribe
    public void handleViewEvent(SecLogoutEvent event) {
        getUI().getSession().close();
        getUI().getPage().setLocation("/");
    }

    @Subscribe
    public void handleViewEvent(SecLoginEvent event) {
        showView(MainView.NAME);
    }

    private void showView(String name) {
        Class c = mapViews.get(name);
        AbstractComponent component = (AbstractComponent) appContext.getBean(c);
        this.setContent(component);
    }

}
