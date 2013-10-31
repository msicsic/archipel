package com.tentelemed.archipel.module.security.event;

import com.tentelemed.archipel.core.event.ApplicationEvent;
import com.tentelemed.archipel.module.security.domain.User;
import com.tentelemed.archipel.module.security.domain.UserId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 31/10/13
 * Time: 17:34
 */
public class SecUserCreatedEvent implements ApplicationEvent {
    UserId userId;
    User user;

    public SecUserCreatedEvent(UserId userId, User user) {
        this.user = user;
        this.userId = userId;
    }
}
