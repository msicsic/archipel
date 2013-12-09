package com.tentelemed.archipel.security.application.service;

import com.google.common.eventbus.Subscribe;
import com.tentelemed.archipel.core.application.event.LogoutRequestEvent;
import com.tentelemed.archipel.core.application.service.EventHandler;
import com.tentelemed.archipel.security.application.service.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 04/11/13
 * Time: 18:14
 */
@Component
@EventHandler
public class UserQueryServiceHandler {

    @Autowired
    UserQueryService userQuery;

    @Subscribe
    public void handleEvent(LogoutRequestEvent event) {
        userQuery.doLogout();
    }
}
