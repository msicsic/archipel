package com.tentelemed.archipel.core.infrastructure.web;

import com.tentelemed.archipel.core.application.DomainEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/10/13
 * Time: 15:33
 */
public class NavigationEvent extends DomainEvent {
    String moduleId;

    public NavigationEvent(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleId() {
        return moduleId;
    }
}
