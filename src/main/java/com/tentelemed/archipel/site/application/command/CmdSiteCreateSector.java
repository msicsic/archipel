package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.site.domain.model.Sector;
import com.tentelemed.archipel.site.domain.model.SiteId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 21:36
 */
public class CmdSiteCreateSector extends Command<SiteId> {
    @NotNull public Sector.Type type;
    @NotNull @Size(min = 3) public String name;
    @NotNull @Size(min = 3) public String code;

    public CmdSiteCreateSector() {
    }

    public CmdSiteCreateSector(SiteId id, Sector.Type type, String name, String code) {
        super(id);
        this.type = type;
        this.name = name;
        this.code = code;
    }
}