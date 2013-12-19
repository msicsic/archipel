package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.site.domain.model.Location;
import com.tentelemed.archipel.site.domain.model.RoomId;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 21:35
 */
public class CmdCreateRoom extends Command<RoomId> {
    @NotNull public String name;
    @NotNull public Location location;
}
