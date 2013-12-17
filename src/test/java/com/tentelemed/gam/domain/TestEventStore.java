package com.tentelemed.gam.domain;

import com.tentelemed.archipel.core.application.EventRegistry;
import com.tentelemed.archipel.core.application.EventStore;
import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.EntityId;
import org.springframework.beans.factory.annotation.Autowired;

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

    int count = 0;
    List<DomainEvent> events = new ArrayList<>();

    protected <U extends BaseAggregateRoot> U handle(U object, DomainEvent event, boolean isNew) {
        if (object == null) {
            throw new RuntimeException("cannot apply event on null instance !");
        }
        try {
            Method method = object.getClass().getMethod("handle", event.getClass());
            method.invoke(object, event);
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
                handle(root, event, false);
            }
        }
        return root;
    }

    @Override
    public <M extends BaseAggregateRoot> M get(Class<M> aggregateClass) {
        try {
            M res = aggregateClass.newInstance();
            res._setId(count++);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleEvents(CmdRes res) {
        for (DomainEvent event : res.events) {
            handle(res.aggregate, event, true);
            if (event.isCreate()) {
                res.aggregate._setId(count++);
            }
        }
    }

    @Override
    public long getMaxAggregateId() {
        return count;
    }
}
