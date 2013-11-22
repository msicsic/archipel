package com.tentelemed.archipel.security.application.service;

import com.tentelemed.archipel.security.application.model.UserDTO;
import com.tentelemed.archipel.security.application.model.UserId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/11/13
 * Time: 12:18
 */
public class CmdUpdateInfo extends Command<UserId> {
    UserDTO info;

    public CmdUpdateInfo(UserId id, UserDTO info) {
        super(id);
        this.info = info;
    }

    public UserDTO getInfo() {
        return info;
    }
}
