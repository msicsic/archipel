package com.tentelemed.archipel.core.infrastructure.config;

import com.tentelemed.archipel.security.application.service.UserServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 31/10/13
 * Time: 15:30
 */
@Component
public class DbInit {

    @Autowired
    UserServiceAdapter service;

    public void initDb() {
        service.createUser("Paul1", "Durand1", "login1", "password1");
        service.createUser("Paul2", "Durand2", "login2", "password2");
    }

}
