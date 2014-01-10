package com.tentelemed.archipel.infrastructure.config;

import com.google.common.eventbus.EventBus;
import com.tentelemed.archipel.core.application.EventRegistry;
import com.tentelemed.archipel.core.application.EventStore;
import com.tentelemed.archipel.core.application.service.CommandServiceFactory;
import com.tentelemed.archipel.core.infrastructure.persistence.handler.TestPersistenceHandler;
import com.tentelemed.archipel.security.application.command.CmdRoleCreate;
import com.tentelemed.archipel.security.application.command.CmdUserCreate;
import com.tentelemed.archipel.security.application.command.RoleCmdHandler;
import com.tentelemed.archipel.security.application.command.UserCmdHandler;
import com.tentelemed.archipel.security.application.event.EvtRoleDomainEvent;
import com.tentelemed.archipel.security.application.event.EvtUserDomainEvent;
import com.tentelemed.archipel.security.application.event.RoleEventHandler;
import com.tentelemed.archipel.security.application.event.UserEventHandler;
import com.tentelemed.archipel.security.domain.model.Role;
import com.tentelemed.archipel.security.domain.model.User;
import com.tentelemed.archipel.security.infrastructure.model.RoleQ;
import com.tentelemed.archipel.security.infrastructure.model.UserQ;
import com.tentelemed.archipel.site.application.command.CmdRoomCreate;
import com.tentelemed.archipel.site.application.command.CmdSiteCreate;
import com.tentelemed.archipel.site.application.command.RoomCmdHandler;
import com.tentelemed.archipel.site.application.command.SiteCmdHandler;
import com.tentelemed.archipel.site.domain.event.EvtRoomDomainEvent;
import com.tentelemed.archipel.site.domain.event.EvtSiteDomainEvent;
import com.tentelemed.archipel.site.domain.event.RoomEventHandler;
import com.tentelemed.archipel.site.domain.event.SiteEventHandler;
import com.tentelemed.archipel.site.domain.model.Room;
import com.tentelemed.archipel.site.domain.model.Site;
import com.tentelemed.archipel.site.infrastructure.model.RoomQ;
import com.tentelemed.archipel.site.infrastructure.model.SiteQ;
import com.tentelemed.gam.domain.TestEventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 23/10/13
 * Time: 12:09
 */
@Configuration
public class TestSpringConfiguration2 {

    @Bean
    public CommandServiceFactory commandServiceFactory() {
        return new CommandServiceFactory();
    }

    @Bean
    public SiteCmdHandler siteCmdHandler() {
        return commandServiceFactory().create(SiteCmdHandler.class);
    }

    @Bean
    public RoomCmdHandler roomCmdHandler() {
        return commandServiceFactory().create(RoomCmdHandler.class);
    }

    @Bean
    public UserCmdHandler userCmdHandler() {
        return commandServiceFactory().create(UserCmdHandler.class);
    }

    @Bean
    public RoleCmdHandler roleCmdHandler() {
        return commandServiceFactory().create(RoleCmdHandler.class);
    }

    @Bean
    public EventRegistry eventRegistry() {
        EventRegistry registry = new EventRegistry();

        registry.addEntry(EvtUserDomainEvent.class, User.class, UserQ.class, UserEventHandler.class);
        registry.addEntry(EvtRoleDomainEvent.class, Role.class, RoleQ.class, RoleEventHandler.class);
        registry.addEntry(EvtSiteDomainEvent.class, Site.class, SiteQ.class, SiteEventHandler.class);
        registry.addEntry(EvtRoomDomainEvent.class, Room.class, RoomQ.class, RoomEventHandler.class);

        registry.addCmdEntry(CmdRoleCreate.class, Role.class);
        registry.addCmdEntry(CmdUserCreate.class, User.class);
        registry.addCmdEntry(CmdRoomCreate.class, Room.class);
        registry.addCmdEntry(CmdSiteCreate.class, Site.class);
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
