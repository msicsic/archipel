package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.command.CmdGroup;
import com.tentelemed.archipel.core.application.command.Command;
import com.tentelemed.archipel.site.domain.pub.SiteId;
import com.tentelemed.archipel.site.domain.pub.SiteType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 21:36
 */
@CmdGroup(module="sites", aggregat = "site")
public class CmdSiteCreate extends Command<SiteId> {
    @NotNull public SiteType type;
    @NotNull @Size(min = 3) public String name;
    @NotNull @Size(min = 3) public String ident;

    public CmdSiteCreate() {
    }

    public CmdSiteCreate(SiteType type, String name, String ident) {
        this.type = type;
        this.name = name;
        this.ident = ident;
    }
}
