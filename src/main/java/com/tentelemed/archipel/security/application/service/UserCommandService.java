package com.tentelemed.archipel.security.application.service;

import com.tentelemed.archipel.core.application.service.BaseCommandService;
import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.security.application.command.*;
import com.tentelemed.archipel.security.domain.model.Role;
import com.tentelemed.archipel.security.domain.model.RoleId;
import com.tentelemed.archipel.security.domain.model.User;
import com.tentelemed.archipel.security.domain.model.UserId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;

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
public class UserCommandService extends BaseCommandService {

    public RoleId execute(CmdDeleteRole cmd) {
        return _execute(cmd);
    }

    public RoleId execute(CmdCreateRole cmd) {
        return _execute(cmd);
    }

    public UserId execute(CmdDeleteUser cmd) {
        return _execute(cmd);
    }

    public UserId execute(CmdCreateUser cmd) {
        return _execute(cmd);
    }

    public UserId execute(CmdUpdateUserInfo cmd) {
        return _execute(cmd);
    }

    CmdRes handle(CmdCreateRole cmd) {
        Role role = get(Role.class);
        return role.register(cmd.name, new HashSet<>(Arrays.asList(cmd.rights)));
    }

    CmdRes handle(CmdCreateUser cmd) {
        User user = get(User.class);
        return user.register(cmd.roleId, cmd.firstName, cmd.lastName, cmd.dob, cmd.email, cmd.login);
    }

    CmdRes handle(CmdUpdateUserInfo cmd) {
        User user = (User) get(cmd.id);
        return user.updateInfo(cmd.firstName, cmd.lastName, cmd.dob, cmd.email);
    }

    CmdRes handle(CmdDeleteUser cmd) {
        User user = (User) get(cmd.id);
        return user.delete();
    }

}
