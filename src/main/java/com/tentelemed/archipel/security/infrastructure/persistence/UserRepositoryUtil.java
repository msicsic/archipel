package com.tentelemed.archipel.security.infrastructure.persistence;

import com.tentelemed.archipel.security.domain.model.User;
import com.tentelemed.archipel.security.infrastructure.persistence.domain.UserHb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Classe utilitaire générée automatiquement par Spring DATA
 * Utilisée par le UserRepository pour fournir des implémentations par default des requetes les plus standards
 *
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 12:20
 */
public interface UserRepositoryUtil extends JpaRepository<UserHb, String> {

    List<UserHb> findByLastName(String lastName);

    @Query(value="select u from UserHb u where \nlower(u.lastName) like ('%' || lower(:name) ||'%')\nor lower(u.firstName) like ('%' || lower(:name) ||'%') ")
    List<UserHb> findByName(@Param("name") String name);

    @Query(value="select u from UserHb u where u.credentials.login=:login")
    UserHb findByLogin(@Param("login") String login);
}
