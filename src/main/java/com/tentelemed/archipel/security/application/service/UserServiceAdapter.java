package com.tentelemed.archipel.security.application.service;

import com.tentelemed.archipel.core.application.DomainEvent;
import com.tentelemed.archipel.core.application.event.LoginEvent;
import com.tentelemed.archipel.core.application.event.LogoutDoneEvent;
import com.tentelemed.archipel.core.application.service.BaseServiceAdapter;
import com.tentelemed.archipel.security.application.model.UserDTO;
import com.tentelemed.archipel.security.domain.interfaces.UserRepository;
import com.tentelemed.archipel.security.domain.model.User;
import com.tentelemed.archipel.security.domain.model.UserId;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class UserServiceAdapter extends BaseServiceAdapter {

    // TODO : le repo ne doit plus etre qu'en lecture seule
    @Autowired
    UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        for (User user : userRepository.getAllUsers()) {
            users.add(UserDTO.fromUser(user));
        }
        return users;
    }

    public void doLogin(String login, String password) throws AuthenticationException {
        UsernamePasswordToken token = new UsernamePasswordToken(login, password);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
        post(new LoginEvent());
    }

    public void doLogout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        post(new LogoutDoneEvent());
    }

    public void createUser(UserDTO userDto) {
        User user = User.createEmptyUser();
        List<DomainEvent<UserId>> events = user.initialise(userDto);
        post(events);
    }

    /**
     * @param userDTO
     * @precond user doit exister (son id ne doit pas etre null)
     * <p/>
     * Mise à jour des infos utilisateur
     * Rq : le login ne peut pas etre changé par ce biais
     */
    public void updateUserInfo(UserDTO userDTO) {
        // chargement de l'agregat
        User user = userRepository.load(userDTO.getEntityId());

        // appel a la méthode métier et récuperation des evenements
        // = application de la commande
        List<DomainEvent<UserId>> events = user.updateInfo(userDTO);

        // traitement par l'EventStore puis propagation
        post(events);
    }

    /**
     * @param id
     * @precond l'utilisateur doit exister
     */
    public void deleteUser(UserId id) {
        User user = userRepository.load(id);
        List<DomainEvent<UserId>> events = user.delete(id);
        post(events);
    }
}
