package com.tentelemed.archipel.web;

import com.tentelemed.archipel.infra.JPAConfiguration;
import com.tentelemed.archipel.module.security.domain.User;
import com.tentelemed.archipel.module.security.repo.UserRepository;
import com.vaadin.annotations.VaadinServletConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ru.xpoft.vaadin.SpringVaadinServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 25/10/13
 * Time: 17:41
 */
@WebServlet(value = "/*",
        asyncSupported = true, initParams = {
        //@WebInitParam(name="contextConfigLocation", value="com.tentelemed.archipel.infra.JPAConfigufation")
})
@VaadinServletConfiguration(
        productionMode = false,
        ui = LoginUI.class)
public class BaseServlet extends SpringVaadinServlet implements WebApplicationInitializer {

    @Override
    public void init() throws ServletException {
        System.err.println("hop");
//        AbstractApplicationContext context = new AnnotationConfigApplicationContext(JPAConfiguration.class);
//        UserRepository repo = context.getBean(UserRepository.class);
//        User user = User.createUser("Paul", "Durand", "login1", "password1");
//        repo.save(user);
//        Iterable<User> users = repo.findAll();
//        System.err.println("hop");
    }

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        System.err.println("hip");
        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(JPAConfiguration.class);

        // Manage the lifecycle of the root application context
        container.addListener(new ContextLoaderListener(rootContext));

        // Create the dispatcher servlet's Spring application context
        AnnotationConfigWebApplicationContext dispatcherContext =
                new AnnotationConfigWebApplicationContext();
//        dispatcherContext.register(DispatcherConfig.class);

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher =
                container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
