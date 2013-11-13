package com.tentelemed.archipel.web;

import com.tentelemed.archipel.security.application.service.UserServiceAdapter;
import com.tentelemed.archipel.security.infrastructure.web.UiLoginViewModel;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 13/11/13
 * Time: 11:55
 */
@Configuration
public class SpringWebTestConfig {

    /*@Bean
    public UiLoginViewModel uiLoginViewModel() {
        return new UiLoginViewModel();
    }*/

//    @Bean(autowire = Autowire.NO)
//    public UserServiceAdapter userServiceAdapter() {
//        return mock(UserServiceAdapter.class);
//    }


}
