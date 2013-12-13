package com.tentelemed.archipel.core.infrastructure.persistence.handler;

import com.google.common.eventbus.Subscribe;
import com.tentelemed.archipel.core.application.EventRegistry;
import com.tentelemed.archipel.core.application.event.AbstractDomainEvent;
import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.application.service.EventHandler;
import com.tentelemed.archipel.core.infrastructure.model.BaseEntityQ;
import com.tentelemed.archipel.core.infrastructure.model.EventUtil;
import com.tentelemed.archipel.security.application.event.RoleDomainEvent;
import com.tentelemed.archipel.security.application.event.UserDomainEvent;
import com.tentelemed.archipel.security.infrastructure.model.RoleQ;
import com.tentelemed.archipel.security.infrastructure.model.UserQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    @Subscribe
    public void handle(AbstractDomainEvent event) throws Exception {
        BaseEntityQ object;
        if (event.isCreate()) {
            object = eventRegistry.newEntityQ(event);
        } else {
            object = em.find(eventRegistry.getClassQForEvent(event), event.getId().getId());
        }
        if (event.isCreate() || event.isUpdate()) {
            EventUtil.applyEvent(object, event);
            em.persist(object);
        } else {
            em.remove(object);
        }
    }

}
