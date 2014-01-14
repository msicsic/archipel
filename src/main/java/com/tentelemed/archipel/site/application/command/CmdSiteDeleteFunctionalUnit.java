package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.command.Command;
import com.tentelemed.archipel.site.domain.pub.SiteId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 21:36
 */
public class CmdSiteDeleteFunctionalUnit extends Command<SiteId> {
    @NotNull @Size(min = 3) public String code;

    public CmdSiteDeleteFunctionalUnit(SiteId id, String code) {
        super(id);
        this.code = code;
    }
}
