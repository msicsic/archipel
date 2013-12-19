package com.tentelemed.archipel.security.application.service;

import com.tentelemed.archipel.core.application.service.BaseCommandService;
import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.core.application.service.CommandHandler;
import com.tentelemed.archipel.core.domain.model.EntityId;
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

    public RoleId execute(final CmdDeleteRole cmd) {
        return _execute(cmd, new CommandHandler<CmdDeleteRole>() {
            @Override
            public CmdRes handle(CmdDeleteRole command) {
                // TODO
                return null;
            }
        });
    }

    public RoleId execute(final CmdCreateRole cmd) {
        return _execute(cmd, new CommandHandler<CmdCreateRole>() {
            @Override
            public CmdRes handle(CmdCreateRole command) {
                Role role = get(Role.class);
                return role.register(cmd.name, new HashSet<>(Arrays.asList(cmd.rights)));
            }
        });
    }

    public UserId execute(final CmdDeleteUser cmd) {
        return _execute(cmd, new CommandHandler<CmdDeleteUser>() {
            @Override
            public CmdRes handle(CmdDeleteUser command) {
                User user = (User) get(cmd.id);
                return user.delete();
            }
        });
    }

    public UserId execute(final CmdCreateUser cmd) {
        return _execute(cmd, new CommandHandler<CmdCreateUser>() {
            @Override
            public CmdRes handle(CmdCreateUser command) {
                User user = get(User.class);
                return user.register(cmd.roleId, cmd.firstName, cmd.lastName, cmd.dob, cmd.email, cmd.login);
            }
        });
    }

    public UserId execute(final CmdUpdateUserInfo cmd) {
        return _execute(cmd, new CommandHandler<CmdUpdateUserInfo>() {
            @Override
            public CmdRes handle(CmdUpdateUserInfo command) {
                User user = (User) get(cmd.id);
                return user.updateInfo(cmd.firstName, cmd.lastName, cmd.dob, cmd.email);
            }
        });
    }

}
