package com.tentelemed.archipel.security.application.event;

import com.tentelemed.archipel.core.application.ApplicationEvent;
import com.tentelemed.archipel.security.application.model.UserDTO;
import com.tentelemed.archipel.security.domain.model.User;
import com.tentelemed.archipel.security.domain.model.UserId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 31/10/13
 * Time: 17:34
 */
public class SecUserCreatedEvent implements ApplicationEvent {
    UserId userId;
    UserDTO user;

    public SecUserCreatedEvent(UserId userId, UserDTO user) {
        this.user = user;
        this.userId = userId;
    }
}
