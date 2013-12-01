package com.tentelemed.archipel.core.infrastructure.config;

import com.tentelemed.archipel.security.application.model.UserDTO;
import com.tentelemed.archipel.security.application.service.UserCommandService;
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
    UserCommandService service;

    public void initDb() {
        for (int i=0; i<100; i++) {
            service.registerUser("Paul"+i, "Durand"+i, new Date(), "mail"+i+"@mail.com", "login"+i);
        }
    }

}
