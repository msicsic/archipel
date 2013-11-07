package com.tentelemed.archipel.security.infrastructure.persistence;

import com.tentelemed.archipel.security.domain.interfaces.UserRepository;
import com.tentelemed.archipel.security.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Le REPO doit manipuler en entr�e sortie des objets BUSINESS et non des objets de la couche persistence
 * Rq : dans le cas particulier de ce repo, la couche persistence utilise directement les objets m�tiers
 *
 * Le REPO Spring n'est pas utilis� directement car il manipule des objets JPA et de plus il pr�sente trop de m�thodes
 * de persistance � la couche sup�rieure
 *
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
    public User save(User user) {
        return repo.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public User findByLogin(String login) {
        return repo.findByLogin(login);
    }
}
