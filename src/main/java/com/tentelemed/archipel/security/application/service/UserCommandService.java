package com.tentelemed.archipel.security.application.service;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.application.service.BaseCommandService;
import com.tentelemed.archipel.security.domain.model.User;
import com.tentelemed.archipel.security.domain.model.UserId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
public class UserCommandService extends BaseCommandService {

    public void registerUser(String firstName, String lastName, Date dob, String email, String login) {
        // c'est une commande de creation d'agregat :
        // il faut donc instancier l'agregat puis lui attribuer un id
        User user = get(User.class);
        List<DomainEvent> events = user.register(user.getEntityId(), firstName, lastName, dob, email, login);
        post(user, events);
    }

    /**
     * @param id
     * @precond user doit exister (son id ne doit pas etre null)
     * <p/>
     * Mise à jour des infos utilisateur
     * Rq : le login ne peut pas etre changé par ce biais
     */
    // TODO : controle de droits
    public void updateUserInfo(UserId id, String firstName, String lastName, Date dob, String email) {
        // chargement de l'agregat
        User user = (User) get(id);

        // appel a la méthode métier et récuperation des evenements
        // = application de la commande
        List<DomainEvent> events = user.updateInfo(firstName, lastName, dob, email);

        // traitement par l'EventStore puis propagation
        post(user, events);
    }

    /**
     * @param id
     * @precond l'utilisateur doit exister
     */
    public void deleteUser(UserId id) {
        User user = (User) get(id);
        List<DomainEvent> events = user.delete(id);
        post(user, events);
    }

//    public void executeCommand(Command command) {
//        UserId id = (UserId) command.getId();
//
//        User user;
//        if (id == null) {
//            user = get(User.class);
//        } else {
//            user = (User) get(id);
//        }
//        List<DomainEvent> events = handleCmd(user, command);
//    }
//
//    private List<DomainEvent> handleCmd(BaseAggregateRoot aggregate, Command cmd) throws Throwable {
//        Method m;
//        try {
//            m = getClass().getMethod("handle", new Class[] {aggregate.getClass(), cmd.getClass()});
//        } catch (Exception e) {
//            log.error("Commnand handler not found : "+cmd.getClass());
//            throw e;
//        }
//        try {
//            return (List<DomainEvent>) m.invoke(this, aggregate, cmd);
//        } catch (InvocationTargetException e) {
//            throw e.getTargetException();
//        }
//    }
//
//    // TODO : controle de droits
//    public List<DomainEvent> handle(User user, CmdUpdateInfo cmd) {
//        return user.updateInfo(cmd.getInfo());
//    }
}
