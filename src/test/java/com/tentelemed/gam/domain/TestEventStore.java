package com.tentelemed.gam.domain;

import com.google.common.eventbus.EventBus;
import com.tentelemed.archipel.core.application.EventRegistry;
import com.tentelemed.archipel.core.application.EventStore;
import com.tentelemed.archipel.core.domain.pub.AbstractDomainEvent;
import com.tentelemed.archipel.core.domain.pub.DomainEvent;
import com.tentelemed.archipel.core.application.command.CmdRes;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.EntityId;
import com.tentelemed.archipel.core.infrastructure.persistence.handler.TestPersistenceHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 11/12/13
 * Time: 15:14
 */
public class TestEventStore implements EventStore {
    @Autowired EventRegistry eventRegistry;
    @Autowired EventBus eventBus;
    @Autowired TestPersistenceHandler pHandler;

    int count = 0;
    List<DomainEvent> events = new ArrayList<>();

    protected <U extends BaseAggregateRoot> U handle(U object, DomainEvent event, boolean isNew) {
        if (object == null) {
            throw new RuntimeException("cannot apply event on null instance !");
        }
        if (event.isDelete()) return null;
        try {
            Method m = object.getClass().getDeclaredMethod("handle", new Class[]{event.getClass()});
            if (event.isCreate()) {
                object._setId(event.getId().getId());
            }
            m.setAccessible(true);
            m.invoke(object, event);
            if (isNew) {
                events.add(event);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return object;
    }

    @Override
    public <M extends EntityId> BaseAggregateRoot<M> get(M id) {
        BaseAggregateRoot<M> root = null;
        for (DomainEvent event : events) {
            if (id.equals(event.getId())) {
                if (root == null) {
                    root = eventRegistry.newAggregateForEvent(event);
                }
                root = handle(root, event, false);
            }
        }
        return root;
    }

    @Override
    public <M extends BaseAggregateRoot> M get(Class<M> c) {
        Constructor cst = null;
        try {
            cst = c.getDeclaredConstructor();
            cst.setAccessible(true);
            M res = (M) cst.newInstance();
            res._setId(count++);
            return res;
        } catch (Exception e) {
            throw new RuntimeException("Cannot instanciate Aggregate : " + c.getSimpleName());
        } finally {
            if (cst != null) {
                cst.setAccessible(false);
            }
        }
    }

    @Override
    public void handleEvents(CmdRes res) {
        // ajout des evts
        events.addAll(res.events);

        // propagation aux QueryManagers (pour maj des bdd de consultation)
        for (AbstractDomainEvent event : res.events) {
            try {
                pHandler.handle(event);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("Error in handler : " + event.getClass().getSimpleName());
            }
        }

        // propagation aux listeners d'evts (les autres modules principalement)
        for (DomainEvent event : res.events) {
            eventBus.post(event);
        }
    }

    @Override
    public long getMaxAggregateId() {
        return count;
    }

    public void clear() {
        count = 0;
        events.clear();
    }
}
