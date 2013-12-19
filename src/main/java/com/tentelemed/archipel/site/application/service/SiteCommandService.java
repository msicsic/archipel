package com.tentelemed.archipel.site.application.service;

import com.tentelemed.archipel.core.application.service.BaseCommandService;
import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.core.application.service.CommandHandler;
import com.tentelemed.archipel.core.domain.model.Address;
import com.tentelemed.archipel.core.domain.model.EntityId;
import com.tentelemed.archipel.site.application.command.*;
import com.tentelemed.archipel.site.domain.model.*;
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
public class SiteCommandService extends BaseCommandService {

    public RoomId execute(final CmdCreateRoom cmd) {
        return _execute(cmd, new CommandHandler<CmdCreateRoom>() {
            @Override
            public CmdRes handle(CmdCreateRoom command) {
                Room room = get(Room.class);
                return room.register(cmd.name, cmd.location);
            }
        });
    }

    public RoomId execute(final CmdUpdateRoom cmd) {
        return _execute(cmd, new CommandHandler<CmdCreateRoom>() {
            @Override
            public CmdRes handle(CmdCreateRoom command) {
                // TODO
                return null;
            }
        });
    }

    public SiteId execute(final CmdCreateService cmd) {
        return _execute(cmd, new CommandHandler<CmdCreateService>() {
            @Override
            public CmdRes handle(CmdCreateService command) {
                Site center = (Site) get(cmd.id);
                return center.createService(cmd.sectorId, cmd.code, cmd.name);
            }
        });
    }

    public SiteId execute(final CmdDeleteSite cmd) {
        return _execute(cmd, new CommandHandler<CmdDeleteSite>() {
            @Override
            public CmdRes handle(CmdDeleteSite command) {
                Site center = (Site) get(cmd.id);
                return center.delete();
            }
        });
    }

    public SiteId execute(final CmdCreateSite cmd) {
        return _execute(cmd, new CommandHandler<CmdCreateSite>() {
            @Override
            public CmdRes handle(CmdCreateSite command) {
                Site center = get(Site.class);
                return center.register(cmd.type, cmd.name, cmd.ident);
            }
        });
    }

    public SiteId execute(final CmdUpdateSite cmd) {
        return _execute(cmd, new CommandHandler<CmdUpdateSite>() {
            @Override
            public CmdRes handle(CmdUpdateSite command) {
                Site center = (Site) get(cmd.id);
                return center.updateMainInfo(cmd.type, cmd.name, cmd.ident);
            }
        });
    }

    public SiteId execute(final CmdUpdateAdditionalInfo cmd) {
        return _execute(cmd, new CommandHandler<CmdUpdateAdditionalInfo>() {
            @Override
            public CmdRes handle(CmdUpdateAdditionalInfo command) {
                Address address = new Address(cmd.street, cmd.postalCode, cmd.town, cmd.countryIso);
                SiteInfo info = new SiteInfo(
                        cmd.siret,
                        address,
                        cmd.phone,
                        cmd.fax,
                        cmd.directorName,
                        cmd.bankCode,
                        cmd.emergenciesAvailable,
                        cmd.drugstoreAvailable,
                        cmd.privateRoomAvailable
                );

                Site center = (Site) get(cmd.id);
                return center.updateAdditionalInfo(info);
            }
        });
    }

    public SiteId execute(final CmdAddSector cmd) {
        return _execute(cmd, new CommandHandler<CmdAddSector>() {
            @Override
            public CmdRes handle(CmdAddSector command) {
                Site site = (Site) get(cmd.id);
                return site.createSector(cmd.type, cmd.code, cmd.name);
            }
        });
    }



}
