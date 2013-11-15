package com.tentelemed.archipel.core.application.event;

import com.tentelemed.archipel.core.application.DomainEvent;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.EntityId;
import com.tentelemed.archipel.security.domain.model.User;
import com.tentelemed.archipel.security.domain.model.UserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
    Logger log = LoggerFactory.getLogger(EventStore.class);

    Map<EntityId, List<DomainEvent>> mapEvents = new HashMap<>();

    public void addEvent(DomainEvent event) {
        List<DomainEvent> events = mapEvents.get(event.getId());
        if (events == null) {
            events = new ArrayList<>();
            mapEvents.put(event.getId(), events);
        }
        events.add(event);
    }

    public <M extends EntityId> BaseAggregateRoot<M> get(M id) {
        List<DomainEvent> events = mapEvents.get(id);
        if (events == null) return null;
        BaseAggregateRoot<M> target = newAgregateRoot(id);
        applyEvents(target, events);
        return target;
    }

    /**
     * Trouver un moyen de faciliter la tache : comment instancier facilement
     * un agregat à partir d'un id ?
     * @param id
     * @return
     */
    private <M extends EntityId> BaseAggregateRoot<M> newAgregateRoot(M id) {
        if (id instanceof UserId) {
            return (BaseAggregateRoot<M>) User.createEmptyUser();
        }
        return null;
    }


    // TODO : traiter les evt DELETE !
    protected void applyEvents(BaseAggregateRoot agregate, Collection<DomainEvent> events) {
        for (DomainEvent event : events) {
            try {
                Method m = agregate.getClass().getMethod("handle", new Class[] {event.getClass()});
                m.invoke(agregate, event);
            } catch (Exception e) {
                log.error("Cant find event handler method : "+agregate.getClass().getSimpleName()+"."+event.getClass().getSimpleName(), e);
            }
        }
    }

}
