package com.tentelemed.archipel.security.infrastructure.persistence.handler;

import com.google.common.eventbus.Subscribe;
import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.application.service.EventHandler;
import com.tentelemed.archipel.core.domain.model.EntityId;
import com.tentelemed.archipel.core.infrastructure.domain.BaseEntityQ;
import com.tentelemed.archipel.core.infrastructure.domain.EntityQUtil;
import com.tentelemed.archipel.security.application.event.RoleDomainEvent;
import com.tentelemed.archipel.security.application.event.UserDomainEvent;
import com.tentelemed.archipel.security.infrastructure.persistence.RoleRepositoryUtil;
import com.tentelemed.archipel.security.infrastructure.persistence.UserRepositoryUtil;
import com.tentelemed.archipel.security.infrastructure.persistence.domain.RoleQ;
import com.tentelemed.archipel.security.infrastructure.persistence.domain.UserQ;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 02/12/13
 * Time: 15:31
 */
@EventHandler("storeEventBus")
@Component
public class PersistenceHandler {

    @Autowired
    RoleRepositoryUtil roleRepo;

    @Autowired
    UserRepositoryUtil userRepo;

    @Subscribe
    public void handle(UserDomainEvent event) {
        UserQ user;
        if (event.isCreate()) {
            user = new UserQ();
        } else {
            user = userRepo.findOne(event.getAggregateId().getId());
        }
        if (event.isCreate() || event.isUpdate()) {
            EntityQUtil.applyEvent(user, event);
            userRepo.save(user);
        } else {
            userRepo.delete(user);
        }
    }

    @Subscribe
    public void handle(RoleDomainEvent event) {
        RoleQ role;
        if (event.isCreate()) {
            role = new RoleQ();
        } else {
            role = roleRepo.findOne(event.getAggregateId().getId());
        }
        if (event.isCreate() || event.isUpdate()) {
            EntityQUtil.applyEvent(role, event);
            roleRepo.save(role);
        } else {
            roleRepo.delete(role);
        }
    }

}
