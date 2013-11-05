package com.tentelemed.archipel.module.security.service;

import com.google.common.eventbus.Subscribe;
import com.tentelemed.archipel.core.event.LogoutRequestEvent;
import com.tentelemed.archipel.core.service.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 04/11/13
 * Time: 18:14
 */
@Component
@EventListener
public class UserServiceListener {

    @Autowired UserService service;

    @Subscribe
    public void handleEvent(LogoutRequestEvent event) {
        service.doLogout();
    }
}
