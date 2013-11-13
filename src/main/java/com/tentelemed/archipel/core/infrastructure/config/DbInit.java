package com.tentelemed.archipel.core.infrastructure.config;

import com.tentelemed.archipel.security.application.model.UserDTO;
import com.tentelemed.archipel.security.application.service.UserServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

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
        for (int i=0; i<100; i++) {
            service.createUser(new UserDTO("Paul"+i, "Durand"+i, "login"+i, "mail1@mail.com", new Date()));
        }
    }

}
