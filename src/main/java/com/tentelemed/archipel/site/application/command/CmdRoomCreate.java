package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.command.Command;
import com.tentelemed.archipel.site.domain.pub.LocationPath;
import com.tentelemed.archipel.site.domain.pub.RoomId;
import com.tentelemed.archipel.site.domain.pub.SiteId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 09/01/14
 * Time: 12:00
 */
public class CmdRoomCreate extends Command<RoomId> {
    public String name;
    public boolean medical;
    public LocationPath locationPath;
    public SiteId siteId;

    public CmdRoomCreate() {
    }

    public CmdRoomCreate(SiteId siteId, String name, boolean medical, LocationPath locationPath) {
        this.siteId = siteId;
        this.name = name;
        this.medical = medical;
        this.locationPath = locationPath;
    }
}
