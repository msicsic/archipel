package com.tentelemed.archipel.core.config;

import com.tentelemed.archipel.module.security.service.UserService;
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
    UserService service;

    public void initDb() {
        service.createUser("Paul1", "Durand1", "login1", "password1");
        service.createUser("Paul2", "Durand2", "login2", "password2");
    }

}
