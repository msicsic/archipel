package com.tentelemed.archipel.core.infrastructure.web;

import com.google.common.base.Objects;
import com.tentelemed.archipel.core.application.service.CoreService;
import com.tentelemed.archipel.core.domain.model.Module;
import com.tentelemed.archipel.security.application.service.RightManager;
import com.tentelemed.archipel.security.application.service.UserQueryService;
import com.tentelemed.archipel.security.domain.pub.RoleQ;
import com.tentelemed.archipel.security.domain.pub.UserQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 04/11/13
 * Time: 14:17
 */
@Component
@Scope("prototype")
public class MainViewModel extends BaseViewModel {

    Module selectedModule;

    @Autowired CoreService service;
    @Autowired UserQueryService userService;
    @Autowired RightManager rightManager;

    public List<Module> getModules() {
        return service.findNonRootModules();
    }

    public void logout() {
        service.logout();
    }

    public void setSelectedModule(Module module) {
        if (!Objects.equal(module, selectedModule)) {
            this.selectedModule = module;
            showView(module.getName());
        }
    }

    public Module getSelectedModule() {
        return selectedModule;
    }

    public String getUserInfo() {
        UserQ user = userService.getCurrentUser();
        if (user == null) return "???";
        return user.getFullName() + " (" + userService.getRoleForUser(user.getEntityId()).getName() + ")";
    }

    public RoleQ getCurrentUserRole() {
        return userService.getCurrentUserRole();
    }

    public boolean isPermitted(String moduleId) {
        return rightManager.isPermitted(moduleId + ":show");
    }
}
