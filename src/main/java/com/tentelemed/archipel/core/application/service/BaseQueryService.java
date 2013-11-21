package com.tentelemed.archipel.core.application.service;

import com.google.common.eventbus.EventBus;
import com.tentelemed.archipel.core.application.ApplicationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:31
 */
public class BaseQueryService {
    protected final static Logger log = LoggerFactory.getLogger(BaseQueryService.class);

    @Autowired
    EventBus eventBus;

    protected void post(ApplicationEvent... events) {
        for (ApplicationEvent event : events) {
            eventBus.post(event);
        }
    }

}
