package com.tentelemed.archipel.core.infrastructure.web;

import com.google.common.base.Objects;
import com.tentelemed.archipel.core.domain.model.Module;
import com.tentelemed.archipel.core.application.service.CoreService;
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
public class MainViewModel extends BasicViewModel {

    Module selectedModule;

    @Autowired
    CoreService service;

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

}
