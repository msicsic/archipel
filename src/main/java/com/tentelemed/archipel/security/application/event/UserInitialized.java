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
public class UserInitialized implements DomainEvent<UserId> {
    final UserId id;
    final UserDTO info;
    final String password;

    public UserInitialized(UserId id, UserDTO info, String password) {
        this.id = id;
        this.info = info;
        this.password = password;
    }

    @Override
    public UserId getId() {
        return id;
    }

    public UserDTO getInfo() {
        return info;
    }

    public String getPassword() {
        return password;
    }
}
