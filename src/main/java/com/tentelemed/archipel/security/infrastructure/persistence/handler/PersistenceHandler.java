package com.tentelemed.archipel.security.infrastructure.persistence.handler;

import com.google.common.eventbus.Subscribe;
import com.tentelemed.archipel.core.application.event.AbstractDomainEvent;
import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.application.service.EventHandler;
import com.tentelemed.archipel.core.infrastructure.model.BaseEntityQ;
import com.tentelemed.archipel.core.infrastructure.model.EntityQUtil;
import com.tentelemed.archipel.security.application.event.RoleDomainEvent;
import com.tentelemed.archipel.security.application.event.UserDomainEvent;
import com.tentelemed.archipel.security.infrastructure.model.RoleQ;
import com.tentelemed.archipel.security.infrastructure.model.UserQ;
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

    @PersistenceContext
    EntityManager em;

    @Subscribe
    public void handle(AbstractDomainEvent event) throws Exception {
        BaseEntityQ object;
        if (event.isCreate()) {
            object = newObjectQ(event).newInstance();
        } else {
            object = em.find(newObjectQ(event), event.getAggregateId().getId());
        }
        if (event.isCreate() || event.isUpdate()) {
            EntityQUtil.applyEvent(object, event);
            em.persist(object);
        } else {
            em.remove(object);
        }
    }

    private Class<? extends BaseEntityQ> newObjectQ(DomainEvent event) {
        if (event instanceof UserDomainEvent) {
            return UserQ.class;
        } else if (event instanceof RoleDomainEvent) {
            return RoleQ.class;
        } else {
            return null;
        }
    }

}