package com.tentelemed.archipel.security.application.command;

import com.tentelemed.archipel.core.application.command.Command;
import com.tentelemed.archipel.security.domain.pub.RoleId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 21:42
 */
public class CmdRoleDelete extends Command<RoleId> {
    public CmdRoleDelete(RoleId id) {
        this.id = id;
    }
}
