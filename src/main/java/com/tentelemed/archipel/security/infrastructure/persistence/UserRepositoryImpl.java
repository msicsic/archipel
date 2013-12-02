package com.tentelemed.archipel.security.infrastructure.persistence;

import com.tentelemed.archipel.security.domain.interfaces.UserRepository;
import com.tentelemed.archipel.security.domain.model.UserId;
import com.tentelemed.archipel.security.infrastructure.persistence.domain.UserQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Le REPO appartient a la couche métier et doit donc prendre en entrée sortie des objets de
 * type métier. Il lui appartient donc de faire les conversions vers le type interne manipulé
 * lors du stockage (par ex des objets annoté hibernate)
 * <p/>
 * Attention cependant ici le pattern CQRS est utilisé, et l'application est donc coupée en 2 :
 * - commandes : couche métier complete, pas de repo mais un EventStore. Les objets metiers ne
 * doivent pas fuir vers les couches supérieures (conversion en objets de la couche evt)
 * Privilégie la pureté du modele métier (ne pas mélanger les aspect techniques)
 * - queries : objets pojo, utilisation d'un repo
 * Privilégie les performances (éviter les traitements inutiles)
 * <p/>
 * <p/>
 * Le REPO doit manipuler en entrée sortie des objets BUSINESS et non des objets de la couche persistence
 * Rq : dans le cas particulier de ce repo, la couche persistence utilise directement les objets métiers
 * <p/>
 * Le REPO Spring n'est pas utilisé directement car il manipule des objets JPA et de plus il présente trop de méthodes
 * de persistance à la couche supérieure
 * <p/>
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 07/11/13
 * Time: 11:48
 */
@Component("userRepository")
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    UserRepositoryUtil repo;

    @Override
    public UserQ save(UserQ user) {
        return repo.save(user);
    }

    @Override
    public List<UserQ> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public UserQ findByLogin(String login) {
        return repo.findByLogin(login);
    }

    @Override
    public UserQ load(UserId id) {
        return repo.findOne(id.getId());
    }

    @Override
    public void deleteUser(UserId id) {
        repo.delete(id.toString());
    }
}
