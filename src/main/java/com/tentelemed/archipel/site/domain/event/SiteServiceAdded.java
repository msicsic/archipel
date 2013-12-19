package com.tentelemed.archipel.site.domain.event;


import com.tentelemed.archipel.site.domain.model.SiteId;
import com.tentelemed.archipel.site.domain.model.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class SiteServiceAdded extends SiteDomainEvent {
    Service service;

    SiteServiceAdded() {
    }

    public SiteServiceAdded(SiteId id, Service service) {
        super(id);
        this.service = service;
    }

    public Service getService() {
        return service;
    }
}
