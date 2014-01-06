package com.tentelemed.archipel.site.application.service;

import com.tentelemed.archipel.core.application.service.BaseCommandService;
import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.application.service.CommandHandler;
import com.tentelemed.archipel.site.application.command.*;
import com.tentelemed.archipel.site.domain.model.Site;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Dans cette implémentation, la couche Application joue ces roles :
 * - orchestration des services applicatifs
 * - gestion des transactions
 * - manipulation du repo
 * - adapter pour la couche supérieure (infra ou client)
 * - reception des commandes clients -> params = DTO et non pas objets métier
 * - ouverture de la TX
 * - chargement de l'agregat concerné
 * - appel aux méthodes metier sur l'agregat
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
public class SiteCommandService extends BaseCommandService implements SiteCmdHandler {

    @Override
    public CmdRes execute(CmdSiteDeleteSector cmd) {
        return _execute(cmd, new CommandHandler<CmdSiteDeleteSector>() {
            @Override
            public CmdRes handle(CmdSiteDeleteSector command) {
                Site site = (Site) get(command.id);
                return site.execute(command);
            }
        });
    }

    /*public CmdRes execute(CmdSiteCreateRoom cmd) {
        return _execute(cmd, new CommandHandler<CmdSiteCreateRoom>() {
            @Override
            public CmdRes handle(CmdSiteCreateRoom cmd) {
                Room room = get(Room.class);
                return room(cmd.name, cmd.location);
            }
        });
    }

    public CmdRes execute(CmdSiteUpdateRoom cmd) {
        return _execute(cmd, new CommandHandler<CmdSiteCreateRoom>() {
            @Override
            public CmdRes handle(CmdSiteCreateRoom cmd) {
                // TODO
                return null;
            }
        });
    } */

    // TODO : simpleExec(cmd) qui fait le traitement de base : validate, get(id), aggregate.execute(cmd), post()

    @Override
    public CmdRes execute(CmdSiteCreateService cmd) {
        return _execute(cmd, new CommandHandler<CmdSiteCreateService>() {
            @Override
            public CmdRes handle(CmdSiteCreateService cmd) {
                Site center = (Site) get(cmd.id);
                return center.execute(cmd);
            }
        });
    }

    @Override
    public CmdRes execute(CmdSiteDeleteService cmd) {
        return _execute(cmd, new CommandHandler<CmdSiteDeleteService>() {
            @Override
            public CmdRes handle(CmdSiteDeleteService command) {
                Site site = (Site) get(command.id);
                return site.execute(command);
            }
        });
    }

    @Override
    public CmdRes execute(CmdSiteDelete cmd) {
        return _execute(cmd, new CommandHandler<CmdSiteDelete>() {
            @Override
            public CmdRes handle(CmdSiteDelete cmd) {
                Site center = (Site) get(cmd.id);
                return center.execute(cmd);
            }
        });
    }

    @Override
    public CmdRes execute(CmdSiteCreate cmd) {
        return _execute(cmd, new CommandHandler<CmdSiteCreate>() {
            @Override
            public CmdRes handle(CmdSiteCreate cmd) {
                Site center = get(Site.class);
                return center.execute(cmd);
            }
        });
    }

    @Override
    public CmdRes execute(CmdSiteUpdate cmd) {
        return _execute(cmd, new CommandHandler<CmdSiteUpdate>() {
            @Override
            public CmdRes handle(CmdSiteUpdate cmd) {
                Site center = (Site) get(cmd.id);
                return center.execute(cmd);
            }
        });
    }

    @Override
    public CmdRes execute(CmdSiteUpdateAdditionalInfo cmd) {
        return _execute(cmd, new CommandHandler<CmdSiteUpdateAdditionalInfo>() {
            @Override
            public CmdRes handle(CmdSiteUpdateAdditionalInfo cmd) {
                Site center = (Site) get(cmd.id);
                return center.execute(cmd);
            }
        });
    }

    @Override
    public CmdRes execute(CmdSiteCreateSector cmd) {
        return _execute(cmd, new CommandHandler<CmdSiteCreateSector>() {
            @Override
            public CmdRes handle(CmdSiteCreateSector cmd) {
                Site site = (Site) get(cmd.id);
                return site.execute(cmd);
            }
        });
    }


}
