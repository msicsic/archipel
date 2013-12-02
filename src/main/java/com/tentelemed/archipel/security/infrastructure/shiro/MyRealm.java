package com.tentelemed.archipel.security.infrastructure.shiro;

import com.tentelemed.archipel.security.application.service.UserQueryService;
import com.tentelemed.archipel.security.domain.model.User;
import com.tentelemed.archipel.security.infrastructure.persistence.domain.UserQ;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
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

        UserQ user = getRepository().findByLogin(upToken.getUsername());
        if (user == null) {
            throw new AuthenticationException("Login name [" + upToken.getUsername() + "] not found!");
        }
        return new SimpleAuthenticationInfo(user.getLogin(), user.getPassword(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Set<String> roles = new HashSet<>();
        Set<Permission> permissions = new HashSet<>();
        Collection<User> principalsList = principals.byType(User.class);

        if (principalsList.isEmpty()) {
            throw new AuthorizationException("Empty principals list!");
        }
        //LOADING STUFF FOR PRINCIPAL
        for (User userPrincipal : principalsList) {
            //User user = repo.findByLogin(userPrincipal.getLogin());

            /*Set<Role> userRoles	= user.getRoles();
            for (Role r : userRoles) {
                roles.add(r.getName());
                Set<WildcardPermission> userPermissions	= r.getPermissions();
                for (WildcardPermission permission : userPermissions) {
                    if (!permissions.contains(permission)) {
                        permissions.add(permission);
                    }
                }
            }*/
        }
        //THIS IS THE MAIN CODE YOU NEED TO DO !!!!
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        info.setRoles(roles); //fill in roles
        info.setObjectPermissions(permissions); //add permisions (MUST IMPLEMENT SHIRO PERMISSION INTERFACE)

        return info;
    }
}
