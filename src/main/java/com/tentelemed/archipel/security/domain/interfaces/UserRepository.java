package com.tentelemed.archipel.security.domain.interfaces;

import com.tentelemed.archipel.security.domain.model.User;
import com.tentelemed.archipel.security.application.model.UserId;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 07/11/13
 * Time: 12:19
 */
public interface UserRepository {

    User save(User user);

    List<User> getAllUsers();

    User findByLogin(String login);

    User load(UserId id);

    void deleteUser(UserId id);
}
