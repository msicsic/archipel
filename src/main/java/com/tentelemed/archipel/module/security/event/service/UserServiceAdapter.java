package com.tentelemed.archipel.module.security.event.service;

import com.tentelemed.archipel.module.security.domain.User;
import com.tentelemed.archipel.module.security.event.domain.UserDTO;
import com.tentelemed.archipel.module.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Converti les objet internes en objets de la couche events
 * API de communication avec l'extérieur ( = "adapter" dans le pattern hexagonal)
 *
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 06/11/13
 * Time: 18:23
 */
@Component
public class UserServiceAdapter {


    @Autowired
    UserService service;

    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        for (User user : service.getAllUsers()) {
            users.add(UserDTO.fromUser(user));
        }
        return users;
    }
}
