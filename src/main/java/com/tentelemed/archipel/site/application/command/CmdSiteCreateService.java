package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.site.domain.model.SiteId;

import javax.validation.constraints.NotNull;

public class CmdSiteCreateService extends Command<SiteId> {
    @NotNull public String sectorCode;
    @NotNull public String code;
    @NotNull public String name;

    public CmdSiteCreateService() {
    }

    public CmdSiteCreateService(SiteId id, String sectorCode, String code, String name) {
        super(id);
        this.sectorCode = sectorCode;
        this.code = code;
        this.name = name;
    }
}