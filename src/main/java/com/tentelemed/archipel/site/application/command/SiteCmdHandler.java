package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.command.CmdRes;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 03/01/14
 * Time: 16:49
 */
public interface SiteCmdHandler {

    CmdRes execute(CmdSiteCreate cmd);

    CmdRes execute(CmdSiteDelete cmd);

    CmdRes execute(CmdSiteUpdate cmd);

    CmdRes execute(CmdSiteUpdateAdditionalInfo cmd);

    CmdRes execute(CmdSiteCreateSector cmd);

    CmdRes execute(CmdSiteDeleteSector cmd);

    CmdRes execute(CmdSiteCreateService cmd);

    CmdRes execute(CmdSiteDeleteService cmd);

    CmdRes execute(CmdSiteDeleteActivityUnit cmd);

    CmdRes execute(CmdSiteCreateActivityUnit cmd);

    CmdRes execute(CmdSiteDeleteFunctionalUnit cmd);

    CmdRes execute(CmdSiteCreateFunctionalUnit cmd);

}
