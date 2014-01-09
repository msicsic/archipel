package com.tentelemed.archipel.security.application.service;

import com.tentelemed.archipel.core.application.service.BaseCommandService;
import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.application.service.CommandHandler;
import com.tentelemed.archipel.security.application.command.*;
import com.tentelemed.archipel.security.domain.model.Role;
import com.tentelemed.archipel.security.domain.model.User;
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
//@Component
@Transactional
public class UserCommandService extends BaseCommandService implements RoleCmdHandler, UserCmdHandler {

    @Override
    public CmdRes execute(CmdRoleDelete cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdRoleUpdateRights cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdRoleCreate cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdUserDelete cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdUserCreate cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdUserUpdateInfo cmd) {
        return genericExec(cmd);
    }

    @Override
    public CmdRes execute(CmdUserChangePassword cmd) {
        return genericExec(cmd);
    }

}
