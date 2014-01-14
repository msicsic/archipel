package com.tentelemed.archipel.security.application.command;

import com.tentelemed.archipel.core.application.command.Command;
import com.tentelemed.archipel.security.domain.pub.UserId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 21:42
 */
public class CmdUserDelete extends Command<UserId> {
    public CmdUserDelete() {
    }

    public CmdUserDelete(UserId id) {
        this.id = id;
    }
}
