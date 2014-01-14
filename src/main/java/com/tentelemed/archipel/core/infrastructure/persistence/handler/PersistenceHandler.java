package com.tentelemed.archipel.core.infrastructure.persistence.handler;

import com.google.common.eventbus.Subscribe;
import com.tentelemed.archipel.core.application.EventRegistry;
import com.tentelemed.archipel.core.domain.pub.AbstractDomainEvent;
import com.tentelemed.archipel.core.application.service.EventHandler;
import com.tentelemed.archipel.core.domain.pub.BaseEntityQ;
import com.tentelemed.archipel.core.infrastructure.model.EventUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 02/12/13
 * Time: 15:31
 */
@EventHandler("storeEventBus")
@Component
public class PersistenceHandler {

    @PersistenceContext EntityManager em;
    @Autowired EventRegistry eventRegistry;
    @Autowired ApplicationContext context;

    @Subscribe
    public void handle(AbstractDomainEvent event) throws Exception {
        BaseEntityQ object;
        if (event.isCreate()) {
            object = eventRegistry.newEntityQ(event);
        } else {
            object = em.find(eventRegistry.getClassQForEvent(event), event.getId().getId());
        }
        if (event.isCreate() || event.isUpdate()) {
            Class handlerClass = eventRegistry.getHandlerClassForEvent(event);
            Object handler = context.getBean(handlerClass);
            Method m = handler.getClass().getMethod("setObject", Object.class);
            m.invoke(handler, object);
            EventUtil.applyEvent(handler, event, false);
            em.persist(object);
        } else {
            em.remove(object);
        }
    }

}
