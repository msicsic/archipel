package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.site.domain.model.LocationPath;
import com.tentelemed.archipel.site.domain.model.RoomId;
import com.tentelemed.archipel.site.domain.model.SiteId;

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
