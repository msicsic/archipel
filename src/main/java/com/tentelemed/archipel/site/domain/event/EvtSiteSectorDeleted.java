package com.tentelemed.archipel.site.domain.event;


import com.tentelemed.archipel.site.domain.model.SiteId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class EvtSiteSectorDeleted extends EvtSiteDomainEvent {
    String sectorCode;

    EvtSiteSectorDeleted() {
    }

    public EvtSiteSectorDeleted(SiteId id, String sectorCode) {
        super(id);
        this.sectorCode = sectorCode;
    }

    public String getSectorCode() {
        return sectorCode;
    }
}
