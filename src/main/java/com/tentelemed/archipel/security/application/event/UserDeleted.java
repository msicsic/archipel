package com.tentelemed.archipel.security.application.event;

import com.tentelemed.archipel.core.application.event.DeleteEvent;
import com.tentelemed.archipel.security.domain.model.UserId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:10
 */
public class UserDeleted extends UserDomainEvent implements DeleteEvent {
    public UserDeleted(UserId id) {
        super(id);
    }
}
