package com.tentelemed.archipel.core.application.service;

import com.google.common.eventbus.EventBus;
import com.tentelemed.archipel.core.application.ApplicationEvent;
import com.tentelemed.archipel.core.application.DomainEvent;
import com.tentelemed.archipel.core.application.event.EventStore;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.security.domain.interfaces.UserRepository;
import com.tentelemed.archipel.security.domain.model.User;
import com.tentelemed.archipel.security.infrastructure.persistence.UserRepositoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:31
 */
public class BaseServiceAdapter {
    protected final static Logger log = LoggerFactory.getLogger(BaseServiceAdapter.class);

    @Autowired
    EventStore eventStore;

    @Autowired
    EventBus eventBus;

    @Autowired
    UserRepositoryUtil repo;

    protected void post(Collection<? extends DomainEvent> events) {
        // stockage et utilisation pour la bdd de requetage
        for (DomainEvent event : events) {
            eventStore.addEvent(event);
            updateQueryStore(event);
        }

        // broadcast des evts
        for (DomainEvent event : events) {
            eventBus.post(event);
        }
    }

    protected void updateQueryStore(DomainEvent event) {
        // TODO  : trouver un moyen generique d'appeler plusieurs queryStores qui construisent chacun leur bdd

        // construction de l'agregat
        BaseAggregateRoot aggregate = eventStore.get(event.getId());
        repo.save((User) aggregate);
    }

    protected <M extends DomainEvent> void post(M... events) {
        post(Arrays.asList(events));
    }

    protected void post(ApplicationEvent... events) {
        for (ApplicationEvent event : events) {
            eventBus.post(event);
        }
    }

}
