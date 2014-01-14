package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.command.Command;
import com.tentelemed.archipel.site.domain.pub.SiteId;

import javax.validation.constraints.NotNull;

public class CmdSiteCreateFunctionalUnit extends Command<SiteId> {
    @NotNull public String parent;
    @NotNull public String code;
    @NotNull public String name;

    public CmdSiteCreateFunctionalUnit() {
    }

    public CmdSiteCreateFunctionalUnit(SiteId id, String parent, String code, String name) {
        super(id);
        this.parent = parent;
        this.code = code;
        this.name = name;
    }
}
