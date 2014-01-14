package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.command.CmdRes;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 03/01/14
 * Time: 16:49
 */
public interface RoomCmdHandler {
    CmdRes execute(CmdRoomCreate cmd);

    CmdRes execute(CmdRoomAddBed cmd);

    CmdRes execute(CmdRoomRemoveBed cmd);
}
