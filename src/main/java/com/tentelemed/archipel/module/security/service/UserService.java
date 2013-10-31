package com.tentelemed.archipel.module.security.service;

import com.google.common.eventbus.EventBus;
import com.tentelemed.archipel.module.security.domain.User;
import com.tentelemed.archipel.module.security.event.SecLoginEvent;
import com.tentelemed.archipel.module.security.event.SecLogoutEvent;
import com.tentelemed.archipel.module.security.event.SecUserCreatedEvent;
import com.tentelemed.archipel.module.security.repo.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    EventBus eventBus;

    public void doLogin(String login, String password) throws AuthenticationException {
        UsernamePasswordToken token = new UsernamePasswordToken(login, password);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
        eventBus.post(new SecLoginEvent());
    }

    public void doLogout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        eventBus.post(new SecLogoutEvent());
    }

    public void createUser(String firstName, String lastName, String login, String password) {
        User user = User.createUser(firstName, lastName, login, password);
        user = repo.save(user);
        eventBus.post(new SecUserCreatedEvent(user.getEntityId(), user));
    }
}

