package com.tentelemed.archipel.security.application.service;

import com.tentelemed.archipel.core.application.event.LoginEvent;
import com.tentelemed.archipel.core.application.service.BaseQueryService;
import com.tentelemed.archipel.security.domain.interfaces.UserRepository;
import com.tentelemed.archipel.security.domain.model.UserId;
import com.tentelemed.archipel.security.infrastructure.model.RoleQ;
import com.tentelemed.archipel.security.infrastructure.model.UserQ;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
public class UserQueryService extends BaseQueryService {

    @Autowired
    UserRepository userRepository;

    public List<UserQ> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public UserQ getCurrentUser() {
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) return null;
        String login = (String) currentUser.getPrincipal();
        return findByLogin(login);
    }

    public RoleQ getRoleForUser(UserId userId) {
        UserQ user = getUser(userId);
        return userRepository.getFindRole(user.getRoleId());
    }

    public RoleQ getCurrentUserRole() {
        return getRoleForUser(getCurrentUser().getEntityId());
    }

    public boolean isPermitted(String right) {
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) return false;
        return currentUser.isPermitted(right);
    }

    public void doLogin(String login, String password) throws AuthenticationException {
        UsernamePasswordToken token = new UsernamePasswordToken(login, password);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
        post(new LoginEvent());
    }

    public void doLogout() {
        Subject currentUser = SecurityUtils.getSubject();
        // attention : "logout" cloture la session http
        currentUser.logout();
    }

    public UserQ getUser(UserId id) {
        return userRepository.load(id);
    }

    public UserQ findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public RoleQ getAnyRole() {
        List<RoleQ> roles = userRepository.getRoles();
        if (roles.isEmpty()) return null;
        return roles.get(0);
    }

}
