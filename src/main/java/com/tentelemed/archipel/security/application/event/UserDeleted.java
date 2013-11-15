package com.tentelemed.archipel.security.application.event;

import com.tentelemed.archipel.core.application.DeleteEvent;
import com.tentelemed.archipel.core.application.DomainEvent;
import com.tentelemed.archipel.security.application.model.UserDTO;
import com.tentelemed.archipel.security.domain.model.UserId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:10
 */
public class UserDeleted implements DeleteEvent<UserId> {
    final UserId id;

    public UserDeleted(UserId id) {
        this.id = id;
    }

    @Override
    public UserId getId() {
        return id;
    }
}
