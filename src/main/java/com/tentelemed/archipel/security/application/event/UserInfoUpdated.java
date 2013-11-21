package com.tentelemed.archipel.security.application.event;

import com.tentelemed.archipel.security.application.model.UserDTO;
import com.tentelemed.archipel.security.domain.model.UserId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:10
 */
public class UserInfoUpdated extends UserDomainEvent {
    final UserDTO info;

    public UserInfoUpdated(UserId id, UserDTO info) {
        super(id);
        this.info = info;
    }

    public UserDTO getInfo() {
        return info;
    }
}
