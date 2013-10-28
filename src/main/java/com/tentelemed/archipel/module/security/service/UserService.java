package com.tentelemed.archipel.module.security.service;

import com.tentelemed.archipel.module.security.domain.User;
import com.tentelemed.archipel.module.security.repo.UserRepository;
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

    public void doLogin(String login, String password) {
        User user = repo.findByLogin(login);
        System.err.println("User : "+user);
    }
}

