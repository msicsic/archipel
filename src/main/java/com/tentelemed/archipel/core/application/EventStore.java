package com.tentelemed.archipel.core.application;

import com.google.common.eventbus.EventBus;
import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.EntityId;
import com.tentelemed.archipel.security.application.event.UserRegistered;
import com.tentelemed.archipel.security.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:55
 */
@Component
public class EventStore {
    private static final Logger log = LoggerFactory.getLogger(EventStore.class);

    @Autowired
    EventBus eventBus;

    Map<EntityId, List<DomainEvent>> mapEvents = new HashMap<>();

    protected <M extends EntityId> void addEvent(DomainEvent<M> event) {
        List<DomainEvent> events = mapEvents.get(event.getAggregateId());
        if (events == null) {
            events = new ArrayList<>();
            mapEvents.put(event.getAggregateId(), events);
        }
        events.add(event);
    }

    public <M extends EntityId> BaseAggregateRoot<M> get(M id) {
        List<DomainEvent> events = mapEvents.get(id);
        if (events == null || events.size() == 0) return null;
        DomainEvent firstEvent = events.get(0);
        if (!(firstEvent.isCreate())) {
            throw new RuntimeException("First event must be CreateEvent");
        }
        BaseAggregateRoot target = newAgregateRoot(firstEvent);
        applyEvents(target, events);
        return target;
    }

    /**
     * Trouver un moyen de faciliter la tache : comment instancier facilement
     * un agregat à partir d'un id ?
     *
     * @param id
     * @return
     */
    private BaseAggregateRoot newAgregateRoot(DomainEvent event) {
        if (event instanceof UserRegistered) {
            return new User();
        }
        return null;
    }

    protected BaseAggregateRoot applyEvents(BaseAggregateRoot aggregate, Collection<? extends DomainEvent> events) {
        for (DomainEvent event : events) {
            if (event.isDelete()) {
                return null;
            }
            try {
                Method m = aggregate.getClass().getMethod("handle", new Class[]{event.getClass()});
                m.invoke(aggregate, event);
            } catch (InvocationTargetException e) {
                throw new RuntimeException((e.getTargetException()));
            } catch (Exception e) {
                log.error("Cant find event handler method : " + aggregate.getClass().getSimpleName() + "." + event.getClass().getSimpleName(), e);
                throw new RuntimeException(e);
            }
        }
        return aggregate;
    }

    public void addAndApplyEvents(BaseAggregateRoot target, Collection<? extends DomainEvent> events) {
        // maj de l'agregat
        Class<? extends BaseAggregateRoot> clazz = target.getClass();
        EntityId id = target.getEntityId();
        target = applyEvents(target, events);

        // ajout des evts dans le store
        for (DomainEvent event : events) {
            addEvent(event);
        }

        // propagation aux QueryManagers (pour maj des bdd de consultation)
        eventBus.post(new QueryUpdateEvent(clazz, id, target, events));

        // propagation aux listeners d'evts (les autres modules principalement)
        for (DomainEvent event : events) {
            eventBus.post(event);
        }
    }
}
