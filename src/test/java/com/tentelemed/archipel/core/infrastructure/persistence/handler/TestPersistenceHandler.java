package com.tentelemed.archipel.core.infrastructure.persistence.handler;

import com.google.common.eventbus.Subscribe;
import com.tentelemed.archipel.core.application.EventRegistry;
import com.tentelemed.archipel.core.application.event.AbstractDomainEvent;
import com.tentelemed.archipel.core.application.service.EventHandler;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.infrastructure.model.BaseEntityQ;
import com.tentelemed.archipel.core.infrastructure.model.EventUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 02/12/13
 * Time: 15:31
 */
@EventHandler("storeEventBus")
@Component
public class TestPersistenceHandler {

    @Autowired EventRegistry eventRegistry;
    @Autowired ApplicationContext context;

    Map<Integer, BaseEntityQ> aggregates = new HashMap<>();

    public void clear() {
        aggregates.clear();
    }

    @Subscribe
    public void handle(AbstractDomainEvent event) throws Exception {
        BaseEntityQ object;
        if (event.isCreate()) {
            object = eventRegistry.newEntityQ(event);
        } else {
            object = find(eventRegistry.getClassQForEvent(event), event.getId().getId());
        }
        if (event.isCreate() || event.isUpdate()) {
            Class handlerClass = eventRegistry.getHandlerClassForEvent(event);
            if (handlerClass == null) {
                throw new RuntimeException("no handler class for event : "+event.getClass().getSimpleName());
            }
            Object handler = context.getBean(handlerClass);
            Method m = handler.getClass().getMethod("setObject", Object.class);
            m.invoke(handler, object);
            EventUtil.applyEvent(handler, event, false);
            persist(object);
        } else {
            remove(object.getId());
        }
    }

    public <M extends BaseEntityQ> M find(Class<M> c, Integer id) {
        return (M) aggregates.get(id);
    }

    private void remove(Integer id) {
        aggregates.remove(id);
    }

    private void persist(BaseEntityQ object) {
        aggregates.put(object.getId(), object);
    }
}
