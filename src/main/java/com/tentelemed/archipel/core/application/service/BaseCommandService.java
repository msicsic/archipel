package com.tentelemed.archipel.core.application.service;

import com.google.common.eventbus.EventBus;
import com.tentelemed.archipel.core.application.ApplicationEvent;
import com.tentelemed.archipel.core.application.EventStore;
import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.EntityId;
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
public class BaseCommandService {
    protected final static Logger log = LoggerFactory.getLogger(BaseCommandService.class);

    @Autowired
    EventStore eventStore;

    @Autowired
    EventBus eventBus;

    protected <M extends EntityId, MM extends BaseAggregateRoot<M>> M post(MM target, Collection<DomainEvent> events) {
        // application des evts sur l'agregat
        eventStore.handleEvents(target, events);
        return target.getEntityId();
    }

    protected <I extends EntityId, M extends DomainEvent<I>> void post(BaseAggregateRoot<I> target, DomainEvent... events) {
        post(target, Arrays.asList(events));
    }

    protected void post(ApplicationEvent... events) {
        for (ApplicationEvent event : events) {
            eventBus.post(event);
        }
    }

    protected <M extends EntityId> BaseAggregateRoot<M> get(M id) {
        return eventStore.get(id);
    }

    protected <M extends BaseAggregateRoot> M get(Class<M> clazz) {
        return eventStore.get(clazz);
    }
}
