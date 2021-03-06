package com.tentelemed.archipel.security.domain.interfaces;

import com.tentelemed.archipel.security.domain.pub.RoleId;
import com.tentelemed.archipel.security.domain.pub.RoleQ;
import com.tentelemed.archipel.security.domain.pub.UserId;
import com.tentelemed.archipel.security.domain.pub.UserQ;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 07/11/13
 * Time: 12:19
 */
public interface UserRepository {

    UserQ save(UserQ user);

    List<UserQ> getAllUsers();

    UserQ findByLogin(String login);

    UserQ load(UserId id);

    void deleteUser(UserId id);

    List<RoleQ> getRoles();

    RoleQ getFindRole(RoleId roleId);

    RoleQ save(RoleQ role);
}
