package com.tentelemed.archipel.security.infrastructure.persistence;

import com.tentelemed.archipel.security.domain.pub.UserQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Classe utilitaire générée automatiquement par Spring DATA
 * Utilisée par le UserRepository pour fournir des implémentations par default des requetes les plus standards
 * <p/>
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 12:20
 */
public interface UserRepositoryUtil extends JpaRepository<UserQ, Integer> {

    List<UserQ> findByLastName(String lastName);

    @Query(value = "select u from UserQ u where \nlower(u.lastName) like ('%' || lower(:name) ||'%')\nor lower(u.firstName) like ('%' || lower(:name) ||'%') ")
    List<UserQ> findByName(@Param("name") String name);

    @Query(value = "select u from UserQ u where u.login=:login")
    UserQ findByLogin(@Param("login") String login);
}
