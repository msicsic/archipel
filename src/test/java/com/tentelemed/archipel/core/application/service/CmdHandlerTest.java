package com.tentelemed.archipel.core.application.service;

import com.tentelemed.archipel.core.infrastructure.persistence.handler.TestPersistenceHandler;
import com.tentelemed.archipel.infrastructure.config.TestSpringConfiguration2;
import com.tentelemed.archipel.security.infrastructure.model.RoleQEventHandler;
import com.tentelemed.archipel.security.infrastructure.model.UserQEventHandler;
import com.tentelemed.archipel.site.infrastructure.model.RoomQEventHandler;
import com.tentelemed.archipel.site.infrastructure.model.SiteQEventHandler;
import com.tentelemed.gam.domain.TestEventStore;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 08/01/14
 * Time: 13:48
 */
public abstract class CmdHandlerTest {

    protected static AnnotationConfigWebApplicationContext context;
    protected TestPersistenceHandler pHandler;
    protected TestEventStore eventStore;

    @BeforeClass
    public static void init() {
        context = new AnnotationConfigWebApplicationContext();
        context.register(TestSpringConfiguration2.class);

        context.register(UserQEventHandler.class);
        context.register(RoleQEventHandler.class);
        context.register(SiteQEventHandler.class);
        context.register(RoomQEventHandler.class);

        context.refresh();
    }

    @Before
    public void setup() {
        pHandler = context.getBean(TestPersistenceHandler.class);
        pHandler.clear();
        eventStore = context.getBean(TestEventStore.class);
        eventStore.clear();
    }
}
