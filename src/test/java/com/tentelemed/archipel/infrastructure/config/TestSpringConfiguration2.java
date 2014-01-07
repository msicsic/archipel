package com.tentelemed.archipel.infrastructure.config;

import com.google.common.eventbus.EventBus;
import com.tentelemed.archipel.core.application.EventRegistry;
import com.tentelemed.archipel.core.application.EventStore;
import com.tentelemed.archipel.core.infrastructure.persistence.handler.TestPersistenceHandler;
import com.tentelemed.archipel.security.application.event.EvtRoleDomainEvent;
import com.tentelemed.archipel.security.application.event.EvtUserDomainEvent;
import com.tentelemed.archipel.security.application.event.RoleEventHandler;
import com.tentelemed.archipel.security.application.event.UserEventHandler;
import com.tentelemed.archipel.security.domain.model.*;
import com.tentelemed.archipel.security.infrastructure.model.RoleQ;
import com.tentelemed.archipel.security.infrastructure.model.UserQ;
import com.tentelemed.archipel.security.infrastructure.model.UserQEventHandler;
import com.tentelemed.archipel.security.infrastructure.shiro.MyRealm;
import com.tentelemed.archipel.site.domain.event.EvtRoomDomainEvent;
import com.tentelemed.archipel.site.domain.event.EvtSiteDomainEvent;
import com.tentelemed.archipel.site.domain.event.RoomEventHandler;
import com.tentelemed.archipel.site.domain.event.SiteEventHandler;
import com.tentelemed.archipel.site.domain.model.Room;
import com.tentelemed.archipel.site.domain.model.Site;
import com.tentelemed.archipel.site.infrastructure.model.RoomQ;
import com.tentelemed.archipel.site.infrastructure.model.SiteQ;
import com.tentelemed.gam.domain.TestEventStore;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.xpoft.vaadin.VaadinMessageSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 23/10/13
 * Time: 12:09
 */
@Configuration
public class TestSpringConfiguration2 {

    @Bean
    public EventRegistry eventRegistry() {
        EventRegistry registry = new EventRegistry();
        registry.addEntry(EvtUserDomainEvent.class, User.class, UserQ.class, UserEventHandler.class);
        registry.addEntry(EvtRoleDomainEvent.class, com.tentelemed.archipel.security.domain.model.Role.class, RoleQ.class, RoleEventHandler.class);
        registry.addEntry(EvtSiteDomainEvent.class, Site.class, SiteQ.class, SiteEventHandler.class);
        registry.addEntry(EvtRoomDomainEvent.class, Room.class, RoomQ.class, RoomEventHandler.class);
        return registry;
    }

    @Bean
    public TestPersistenceHandler persistenceHandler() {
        return new TestPersistenceHandler();
    }

    @Bean
    public EventStore eventStore() {
        return new TestEventStore();
    }

    @Bean(name = "eventBus")
    public EventBus eventBus() {
        return new EventBus();
    }

    @Bean(name = "storeEventBus")
    public EventBus storeEventBus() {
        return new EventBus();
    }

    @Bean(name = "localBus")
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public EventBus sessionEventBus() {
        return new EventBus();
    }

}
