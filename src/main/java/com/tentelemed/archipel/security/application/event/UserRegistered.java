package com.tentelemed.archipel.security.application.event;

import com.tentelemed.archipel.security.application.model.UserDTO;
import com.tentelemed.archipel.security.domain.model.UserId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:10
 */
public class UserRegistered extends UserDomainEvent {
    UserDTO info;
    String password;

    public UserRegistered(UserId id, UserDTO info, String password) {
        this.id = id;
        this.info = info;
        this.password = password;
    }

    public UserDTO getInfo() {
        return info;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public Type getType() {
        return Type.CREATE;
    }
}
