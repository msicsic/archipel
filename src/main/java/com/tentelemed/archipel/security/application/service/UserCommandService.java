package com.tentelemed.archipel.security.application.service;

import com.tentelemed.archipel.core.application.service.BaseCommandService;
import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.security.domain.model.*;
import org.hibernate.validator.constraints.Email;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Date;
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

    public static class CmdRegisterRole extends Command<RoleId> {
        @NotNull @Size(min = 3, max = 32) public String name;
        @NotNull public Right[] rights;

        public CmdRegisterRole(String name, Right... rights) {
            this.name = name;
            this.rights = rights;
        }
    }

    public static class CmdDeleteRole extends Command<RoleId> {
        public CmdDeleteRole(RoleId id) {
            this.id = id;
        }
    }

    public final static class CmdRegisterUser extends Command<UserId> {
        @NotNull public RoleId roleId;
        @NotNull public String firstName;
        @NotNull public String lastName;
        public Date dob;
        @Email public String email;
        @NotNull public String login;

        public CmdRegisterUser() {
        }

        public CmdRegisterUser(RoleId roleId, String firstName, String lastName, Date dob, String email, String login) {
            this.roleId = roleId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.dob = dob;
            this.email = email;
            this.login = login;
        }
    }

    public static class CmdUpdateUserInfo extends Command<UserId> {
        @NotNull public String firstName;
        @NotNull public String lastName;
        public Date dob;
        @NotNull public String email;

        public CmdUpdateUserInfo() {
        }

        public CmdUpdateUserInfo(UserId id, String firstName, String lastName, Date dob, String email) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.dob = dob;
            this.email = email;
        }
    }

    public static class CmdDeleteUser extends Command<UserId> {
        public CmdDeleteUser(UserId id) {
            this.id = id;
        }
    }

    CmdRes handle(CmdRegisterRole cmd) {
        Role role = get(Role.class);
        return role.register(cmd.name, new HashSet<>(Arrays.asList(cmd.rights)));
    }

    CmdRes handle(CmdRegisterUser cmd) {
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
