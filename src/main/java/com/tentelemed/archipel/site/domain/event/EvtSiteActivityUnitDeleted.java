package com.tentelemed.archipel.site.domain.event;


import com.tentelemed.archipel.site.domain.model.SiteId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class EvtSiteActivityUnitDeleted extends EvtSiteDomainEvent {
    String code;

    EvtSiteActivityUnitDeleted() {
    }

    public EvtSiteActivityUnitDeleted(SiteId id, String code) {
        super(id);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}