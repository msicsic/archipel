package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.site.domain.model.SiteId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 21:36
 */
public class CmdSiteDeleteSector extends Command<SiteId> {
    @NotNull @Size(min = 3) public String code;

    public CmdSiteDeleteSector(SiteId id, String code) {
        super(id);
        this.code = code;
    }
}
