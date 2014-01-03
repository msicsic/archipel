package com.tentelemed.archipel.security.application.command;

import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.security.domain.model.RoleId;

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
