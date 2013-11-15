package com.tentelemed.archipel.security.application.event;

import com.tentelemed.archipel.core.application.DomainEvent;
import com.tentelemed.archipel.security.application.model.UserDTO;
import com.tentelemed.archipel.security.domain.model.UserId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:10
 */
public class UserInfoUpdated implements DomainEvent<UserId> {
    final UserId id;
    final UserDTO info;

    public UserInfoUpdated(UserId id, UserDTO info) {
        this.id = id;
        this.info = info;
    }

    @Override
    public UserId getId() {
        return id;
    }

    public UserDTO getInfo() {
        return info;
    }
}
