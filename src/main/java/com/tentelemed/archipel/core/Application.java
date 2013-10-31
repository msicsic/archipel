package com.tentelemed.archipel.core;

import com.tentelemed.archipel.core.config.SpringConfiguration;
import com.tentelemed.archipel.module.security.domain.User;
import com.tentelemed.archipel.module.security.repo.UserRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 16:07
 */
public class Application {

    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        UserRepository repo = context.getBean(UserRepository.class);
        User user = User.createUser("Paul", "Durand", "login1", "password1");
        repo.save(user);
        Iterable<User> users = repo.findAll();
        System.err.println("hop");
    }
}
