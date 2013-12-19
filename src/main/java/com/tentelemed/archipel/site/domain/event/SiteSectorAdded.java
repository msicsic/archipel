package com.tentelemed.archipel.site.domain.event;


import com.tentelemed.archipel.site.domain.model.SiteId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class SiteSectorAdded extends SiteDomainEvent {
    String sectorCode;
    String sectorName;

    SiteSectorAdded() {
    }

    public SiteSectorAdded(SiteId id, String sectorCode, String sectorName) {
        super(id);
        this.sectorCode = sectorCode;
        this.sectorName = sectorName;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public String getSectorName() {
        return sectorName;
    }
}
