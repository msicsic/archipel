package com.tentelemed.archipel.core.infrastructure.config;

import com.google.common.eventbus.EventBus;
import com.tentelemed.archipel.core.application.service.CoreService;
import com.tentelemed.archipel.core.infrastructure.web.MyUI;
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
 * <p/>
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 25/10/13
 * Time: 17:41
 */
@WebServlet(value = {"/*", "/VAADIN/*"},
        asyncSupported = true, initParams = {
        @WebInitParam(name = "beanName", value = "myUI")
})
@VaadinServletConfiguration(
        productionMode = false,
        ui = MyUI.class)
public class VaadinServlet extends SpringVaadinServlet implements WebApplicationInitializer {

    private AnnotationConfigWebApplicationContext context;

    @Override
    public void init() throws ServletException {
        System.err.println("VaadinServlet.init");
    }

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        System.err.println("VaadinServlet.onStartup");
        // Create the 'root' Spring application context
        context = new AnnotationConfigWebApplicationContext();
        context.register(SpringConfiguration.class);
        context.refresh();

        // Manage the lifecycle of the root application context
        container.addListener(new ContextLoaderListener(context));

        CoreService service = context.getBean(CoreService.class);
        service.initApplication();

        EventBus eventBus = (EventBus) context.getBean("eventBus");
        eventBus.register(this);

        // init de la bdd de test
        DbInit init = context.getBean(DbInit.class);
        if (init != null) init.initDb();
    }
}
