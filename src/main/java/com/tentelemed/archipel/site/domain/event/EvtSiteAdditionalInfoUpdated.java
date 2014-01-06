package com.tentelemed.archipel.site.domain.event;


import com.tentelemed.archipel.site.domain.model.SiteId;
import com.tentelemed.archipel.site.domain.model.SiteInfo;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class EvtSiteAdditionalInfoUpdated extends EvtSiteDomainEvent {

    private SiteInfo info;

    EvtSiteAdditionalInfoUpdated() {
    }

    public EvtSiteAdditionalInfoUpdated(SiteId id, SiteInfo info) {
        super(id);
        this.info = info;
    }

    public SiteInfo getInfo() {
        return info;
    }
}
