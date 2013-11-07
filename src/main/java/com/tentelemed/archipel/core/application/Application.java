package com.tentelemed.archipel.core.application;

import com.tentelemed.archipel.core.infrastructure.config.SpringConfiguration;
import com.tentelemed.archipel.security.domain.model.User;
import com.tentelemed.archipel.security.infrastructure.persistence.UserRepositoryUtil;
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
        UserRepositoryUtil repo = context.getBean(UserRepositoryUtil.class);
        User user = User.createUser("Paul", "Durand", "login1", "password1");
        repo.save(user);
        Iterable<User> users = repo.findAll();
        System.err.println("hop");
    }
}
