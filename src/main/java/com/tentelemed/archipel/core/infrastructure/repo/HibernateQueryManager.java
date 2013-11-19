package com.tentelemed.archipel.core.infrastructure.repo;

import com.google.common.eventbus.Subscribe;
import com.tentelemed.archipel.core.application.QueryUpdateEvent;
import com.tentelemed.archipel.core.application.service.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/11/13
 * Time: 12:18
 */
@Component
@EventListener
public class HibernateQueryManager {

    @Autowired
    JpaRepository repo;

    @Subscribe
    public void handleEvent(QueryUpdateEvent event) {
        if (event.getAggregate() == null) {
            repo.delete(event.getId().getId());
        } else {
            Object res = repo.save(event.getAggregate());
            System.err.println("res : "+res);
        }
    }
}
