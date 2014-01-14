package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.command.Command;
import com.tentelemed.archipel.site.domain.pub.SiteId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 21:37
 */
public class CmdSiteDelete extends Command<SiteId> {
    public CmdSiteDelete(SiteId id) {
        super(id);
    }
}
