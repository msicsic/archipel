package com.tentelemed.archipel.site.domain.event;


import com.tentelemed.archipel.site.domain.model.SiteId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class EvtSiteServiceDeleted extends EvtSiteDomainEvent {
    String serviceCode;

    EvtSiteServiceDeleted() {
    }

    public EvtSiteServiceDeleted(SiteId id, String serviceCode) {
        super(id);
        this.serviceCode = serviceCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }
}
