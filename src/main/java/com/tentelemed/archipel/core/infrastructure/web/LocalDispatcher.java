package com.tentelemed.archipel.core.infrastructure.web;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Repost events send on the application bus on the session bus
 *
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 21/11/13
 * Time: 12:02
 */
@Component
public class LocalDispatcher {

    @Autowired
    @Qualifier("localBus")
    EventBus localBus;

    @Subscribe
    public void dispatch(Object event) {
        localBus.post(event);
    }
}
