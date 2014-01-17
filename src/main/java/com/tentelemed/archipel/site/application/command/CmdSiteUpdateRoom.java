package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.command.CmdGroup;
import com.tentelemed.archipel.core.application.command.Command;
import com.tentelemed.archipel.site.domain.pub.Location;
import com.tentelemed.archipel.site.domain.pub.RoomId;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 21:36
 */
@CmdGroup(module="sites", aggregat = "site")
public class CmdSiteUpdateRoom extends Command<RoomId> {
    @NotNull public String name;
    public boolean medical;
    @NotNull public Location location;
}
