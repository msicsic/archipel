package com.tentelemed.archipel.security.infrastructure.persistence.handler;

import com.google.common.eventbus.Subscribe;
import com.tentelemed.archipel.core.application.service.EventHandler;
import com.tentelemed.archipel.security.application.event.UserDeleted;
import com.tentelemed.archipel.security.application.event.UserInfoUpdated;
import com.tentelemed.archipel.security.application.event.UserPasswordUpdated;
import com.tentelemed.archipel.security.application.event.UserRegistered;
import com.tentelemed.archipel.security.infrastructure.persistence.domain.UserQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 02/12/13
 * Time: 15:31
 */
@EventHandler("storeEventBus")
@Component
public class UserHandler {

    @Autowired
    JpaRepository repo;

    @Subscribe
    public void handle(UserRegistered event) {
        UserQ user = new UserQ();
        user.setId(event.id.getId());
        user.setDob(event.dob);
        user.setEmail(event.email);
        user.setFirstName(event.firstName);
        user.setLastName(event.lastName);
        user.setLogin(event.credentials.getLogin());
        user.setPassword(event.credentials.getPassword());
        repo.save(user);
    }

    @Subscribe
    public void handle(UserDeleted event) {
        repo.delete(event.getAggregateId().getId());
    }

    @Subscribe
    public void handle(UserInfoUpdated event) {
        UserQ user = (UserQ) repo.findOne(event.id.getId());
        user.setFirstName(event.firstName);
        user.setLastName(event.lastName);
        user.setDob(event.dob);
        user.setEmail(event.email);
    }

    @Subscribe
    public void handle(UserPasswordUpdated event) {
        UserQ user = (UserQ) repo.findOne(event.id.getId());
        user.setPassword(event.password);
    }

}
