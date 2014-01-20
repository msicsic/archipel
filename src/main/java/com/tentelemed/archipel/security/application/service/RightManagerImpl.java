package com.tentelemed.archipel.security.application.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 20/01/14
 * Time: 13:59
 */
@Component
@Qualifier("rigthManager")
public class RightManagerImpl implements RightManager {

    @Override
    public boolean isPermitted(String toTest) {
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) return false;
        return currentUser.isPermitted(toTest);
    }
}
