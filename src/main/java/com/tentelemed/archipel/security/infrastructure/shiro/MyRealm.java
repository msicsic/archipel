package com.tentelemed.archipel.security.infrastructure.shiro;

import com.tentelemed.archipel.security.application.service.UserQueryService;
import com.tentelemed.archipel.security.domain.pub.Right;
import com.tentelemed.archipel.security.domain.pub.RoleQ;
import com.tentelemed.archipel.security.domain.pub.UserQ;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 31/10/13
 * Time: 15:02
 */
@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    ApplicationContext context;

    // Rq : '@Autowired' pas possible ici car composant instancié avant la dépendance...
    private UserQueryService getRepository() {
        return (UserQueryService) context.getBean("userQueryService");
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        if (upToken.getUsername().equals("superAdmin") && new String(upToken.getPassword()).equals("verySecretPass")) {
            return new SimpleAuthenticationInfo(upToken.getUsername(), new String(upToken.getPassword()), getName());
        } else {
            UserQ user = getRepository().findByLogin(upToken.getUsername());
            if (user == null) {
                throw new AuthenticationException("Login name [" + upToken.getUsername() + "] not found!");
            }
            return new SimpleAuthenticationInfo(user.getLogin(), user.getPassword(), getName());
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Set<String> roles = new HashSet<>();
        Collection<String> principalsList = principals.byType(String.class);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);

        if (principalsList.isEmpty()) {
            throw new AuthorizationException("Empty principals list!");
        }
        //LOADING STUFF FOR PRINCIPAL
        Set<String> perms = new HashSet<>();
        for (String login : principalsList) {
            if (login.equals("superAdmin")) {
                roles.add("superAdmin");
                perms.add("*");
            } else {
                UserQ user = getRepository().findByLogin(login);
                RoleQ role = getRepository().getRoleForUser(user.getEntityId());
                roles.add(role.getName());
                //Set<WildcardPermission> userPermissions	= r.getPermissions();
                for (Right r : role.getRights()) {
                    perms.add(r.getPermissions());
                }
            }
        }

        //fill in roles
        info.setRoles(roles);

        info.setStringPermissions(perms);
        return info;
    }
}
