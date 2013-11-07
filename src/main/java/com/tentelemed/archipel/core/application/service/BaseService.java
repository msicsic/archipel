package com.tentelemed.archipel.core.application.service;

import com.google.common.eventbus.EventBus;
import com.tentelemed.archipel.core.application.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 05/11/13
 * Time: 14:29
 */
public class BaseService {

    protected static final Logger log = LoggerFactory.getLogger(BaseService.class);

    @Autowired
    EventBus eventBus;

    protected void fire(DomainEvent event) {
        eventBus.post(event);
    }
}
