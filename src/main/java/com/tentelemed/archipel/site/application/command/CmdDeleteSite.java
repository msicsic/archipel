package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.site.domain.model.SiteId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 21:37
 */
public class CmdDeleteSite extends Command<SiteId> {
    public CmdDeleteSite(SiteId id) {
        super(id);
    }
}
