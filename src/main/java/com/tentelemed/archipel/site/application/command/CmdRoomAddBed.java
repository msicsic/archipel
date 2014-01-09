package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.site.domain.model.Bed;
import com.tentelemed.archipel.site.domain.model.LocationPath;
import com.tentelemed.archipel.site.domain.model.RoomId;
import com.tentelemed.archipel.site.domain.model.SiteId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 09/01/14
 * Time: 12:00
 */
public class CmdRoomAddBed extends Command<RoomId> {
    public Bed bed;

    public CmdRoomAddBed() {
    }

    public CmdRoomAddBed(RoomId roomId, Bed bed) {
        this.id = roomId;
        this.bed = bed;
    }
}
