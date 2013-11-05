package com.tentelemed.archipel.core.web;

import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.xpoft.vaadin.VaadinMessageSource;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/10/13
 * Time: 10:53
 */
public class BasicViewModel {
    @Autowired
    EventBus eventBus;

    @Autowired
    protected VaadinMessageSource msg;

    protected static final Logger log = LoggerFactory.getLogger(BasicViewModel.class);

    /*protected void fire(NavigationEvent event) {
        eventBus.post(event);
    }*/

    protected void showView(String viewId) {
        eventBus.post(new NavigationEvent(viewId));
    }

    protected String getText(String key) {
        return msg.getMessage(getClass().getSimpleName()+"."+key);
    }

    protected String gt(String key) {
        return getText(key);
    }
}
