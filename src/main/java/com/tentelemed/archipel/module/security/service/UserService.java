package com.tentelemed.archipel.module.security.service;

import com.tentelemed.archipel.module.security.domain.User;
import com.tentelemed.archipel.module.security.repo.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.LoginException;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 28/10/13
 * Time: 11:36
 */
@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository repo;

    public void doLogin(String login, String password) throws AuthenticationException {
        //User user = repo.findByLogin(login);
        UsernamePasswordToken token = new UsernamePasswordToken(login, password);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
    }

    public void doLogout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
    }
}

