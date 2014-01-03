package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.service.CmdRes;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 03/01/14
 * Time: 16:49
 */
public interface CmdHandlerSite {
    CmdRes execute(CmdSiteCreate cmd);
    CmdRes execute(CmdSiteDelete cmd);
    CmdRes execute(CmdSiteUpdate cmd);
    CmdRes execute(CmdSiteUpdateAdditionalInfo cmd);
    CmdRes execute(CmdSiteCreateSector cmd);
    CmdRes execute(CmdSiteDeleteSector cmd);
    CmdRes execute(CmdSiteCreateService cmd);
    CmdRes execute(CmdSiteDeleteService cmd);

}
