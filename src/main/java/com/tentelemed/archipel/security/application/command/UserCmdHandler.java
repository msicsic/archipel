package com.tentelemed.archipel.security.application.command;

import com.tentelemed.archipel.core.application.service.CmdRes;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 03/01/14
 * Time: 16:00
 */
public interface UserCmdHandler {
    CmdRes execute(CmdUserCreate cmd);

    CmdRes execute(CmdUserDelete cmd);

    CmdRes execute(CmdUserUpdateInfo cmd);

    CmdRes execute(CmdUserChangePassword cmd);
}
