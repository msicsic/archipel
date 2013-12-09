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

    protected <M extends EntityId, MM extends BaseAggregateRoot<M>> MM post(MM target, Collection<? extends DomainEvent> events) {
        // application des evts sur l'agregat
        eventStore.handleEvents(target, events);
        return target;
    }

    protected <I extends EntityId, M extends DomainEvent<I>> void post(BaseAggregateRoot<I> target, M... events) {
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

    static long count = -1;

    private void initCount() {
        if (count == -1) {
            count = eventStore.getMaxAggregateId();
        }
    }

    private Integer nextId() {
        initCount();
        return (int)(count++);
    }

    protected <M extends BaseAggregateRoot> M get(Class<M> clazz) {
        try {
            M res = clazz.newInstance();
            res._setId(nextId());
            return res;
        } catch (Exception e) {
            log.error(null, e);
            throw new RuntimeException(e);
        }
    }
}
