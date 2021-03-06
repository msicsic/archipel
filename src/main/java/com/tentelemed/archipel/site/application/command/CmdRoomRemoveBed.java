package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.command.CmdGroup;
import com.tentelemed.archipel.core.application.command.Command;
import com.tentelemed.archipel.site.domain.pub.Bed;
import com.tentelemed.archipel.site.domain.pub.RoomId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 09/01/14
 * Time: 12:00
 */
@CmdGroup(module="sites", aggregat = "room")
public class CmdRoomRemoveBed extends Command<RoomId> {
    public Bed bed;

    public CmdRoomRemoveBed() {
    }

    public CmdRoomRemoveBed(RoomId roomId, Bed bed) {
        this.id = roomId;
        this.bed = bed;
    }
}
