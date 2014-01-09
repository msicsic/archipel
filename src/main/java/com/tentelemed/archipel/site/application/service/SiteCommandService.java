package com.tentelemed.archipel.site.application.service;

import com.tentelemed.archipel.core.application.service.BaseCommandService;
import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.application.service.CommandHandler;
import com.tentelemed.archipel.site.application.command.*;
import com.tentelemed.archipel.site.domain.model.Room;
import com.tentelemed.archipel.site.domain.model.Site;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Dans cette implémentation, la couche Application joue ces roles :
 * - gestion des transactions
 * - manipulation du repo
 * - adapter pour la couche supérieure (infra ou client)
 * - reception des commandes clients -> params = Valeurs (primitifs et VO) et non pas objets métier
 * - ouverture de la TX
 * - chargement de l'agregat concerné
 * - chargement d'eventuels agregats liés -> stockage dans buffer temporaire de la commande
 * - la commande est ensuite déléguée à l'agregat
 * - fermeture de la TX
 * - envoi des EVTs (contient des DTO et non pas des objets métiers)
 * <p/>
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 06/11/13
 * Time: 18:23
 */
@Component
@Transactional
public class SiteCommandService extends BaseCommandService implements SiteCmdHandler, RoomCmdHandler {

    @Override
    public CmdRes execute(CmdSiteDeleteSector cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdSiteCreateService cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdSiteDeleteService cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdSiteDelete cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdSiteCreate cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdSiteUpdate cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdSiteUpdateAdditionalInfo cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdSiteCreateSector cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdSiteCreateFunctionalUnit cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdSiteDeleteFunctionalUnit cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdSiteCreateActivityUnit cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdSiteDeleteActivityUnit cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdRoomCreate cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdRoomAddBed cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdRoomRemoveBed cmd) {
        return genericExec(cmd);
    }
}
