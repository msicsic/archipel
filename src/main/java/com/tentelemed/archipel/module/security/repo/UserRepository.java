package com.tentelemed.archipel.module.security.repo;

import com.tentelemed.archipel.module.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 12:20
 */
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByLastName(String lastName);

    @Query(value="select u from User u where \nlower(u.lastName) like ('%' || lower(:name) ||'%')\nor lower(u.firstName) like ('%' || lower(:name) ||'%') ")
    List<User> findByName(@Param("name") String name);

    @Query(value="select u from User u where u.credentials.login=:login")
    User findByLogin(@Param("login") String login);
}
