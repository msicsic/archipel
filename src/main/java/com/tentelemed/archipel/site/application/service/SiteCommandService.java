package com.tentelemed.archipel.site.application.service;

import com.tentelemed.archipel.core.application.service.BaseCommandService;
import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.domain.model.Address;
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

    public RoomId execute(CmdCreateRoom cmd) {
        return _execute(cmd);
    }

    public RoomId execute(CmdUpdateRoom cmd) {
        return _execute(cmd);
    }

    public SiteId execute(CmdCreateService cmd) {
        return _execute(cmd);
    }

    public SiteId execute(CmdDeleteSite cmd) {
        return _execute(cmd);
    }

    public SiteId execute(CmdCreateSite cmd) {
        return _execute(cmd);
    }

    public SiteId execute(CmdUpdateSite cmd) {
        return _execute(cmd);
    }

    public SiteId execute(CmdUpdateAdditionalInfo cmd) {
        return _execute(cmd);
    }

    public SiteId execute(CmdAddSector cmd) {
        return _execute(cmd);
    }

    CmdRes handle(CmdAddSector cmd) {
        Site site = (Site) get(cmd.id);
        return site.createSector(cmd.type, cmd.code, cmd.name);
    }

    CmdRes handle(CmdCreateService cmd) {
        Site center = (Site) get(cmd.id);
        return center.createService(cmd.sectorId, cmd.code, cmd.name);
    }

    CmdRes handle(CmdCreateRoom cmd) {
        Room room = get(Room.class);
        return room.register(cmd.name, cmd.location);
    }

    CmdRes handle(CmdCreateSite cmd) {
        Site center = get(Site.class);
        return center.register(cmd.type, cmd.name, cmd.ident);
    }

    CmdRes handle(CmdUpdateSite cmd) {
        Site center = (Site) get(cmd.id);
        return center.updateMainInfo(cmd.type, cmd.name, cmd.ident);
    }

    CmdRes handle(CmdDeleteSite cmd) {
        Site center = (Site) get(cmd.id);
        return center.delete();
    }

    CmdRes handle(CmdUpdateAdditionalInfo cmd) {
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
}
