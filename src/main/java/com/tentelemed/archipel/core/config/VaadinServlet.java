package com.tentelemed.archipel.core.config;

import com.tentelemed.archipel.core.config.DbInit;
import com.tentelemed.archipel.core.config.SpringConfiguration;
import com.tentelemed.archipel.core.web.MyUI;
import com.vaadin.annotations.VaadinServletConfiguration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import ru.xpoft.vaadin.SpringVaadinServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Controleur principal de Vaadin
 *
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 25/10/13
 * Time: 17:41
 */
@WebServlet(value = {"/*", "/VAADIN/*"},
        asyncSupported = true, initParams = {
        @WebInitParam(name="beanName", value="myUI")
})
@VaadinServletConfiguration(
        productionMode = false,
        ui = MyUI.class)
public class VaadinServlet extends SpringVaadinServlet implements WebApplicationInitializer {

    @Override
    public void init() throws ServletException {
        System.err.println("VaadinServlet.init");
    }

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        System.err.println("VaadinServlet.onStartup");
        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(SpringConfiguration.class);
        rootContext.refresh();

        // Manage the lifecycle of the root application context
        container.addListener(new ContextLoaderListener(rootContext));

        // init de la bdd de test
        DbInit init = rootContext.getBean(DbInit.class);
        if (init != null) init.initDb();
    }

}