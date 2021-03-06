package com.tentelemed.archipel.security.application.command;

import com.tentelemed.archipel.core.application.command.CmdRes;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 03/01/14
 * Time: 16:00
 */
public interface RoleCmdHandler {
    CmdRes execute(CmdRoleCreate cmd);

    CmdRes execute(CmdRoleDelete cmd);

    CmdRes execute(CmdRoleUpdateRights cmd);
}
