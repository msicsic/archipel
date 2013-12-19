package com.tentelemed.archipel.site.domain.event;


import com.tentelemed.archipel.site.domain.model.SectorId;
import com.tentelemed.archipel.site.domain.model.Service;
import com.tentelemed.archipel.site.domain.model.SiteId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class SiteSectorAdded extends SiteDomainEvent {
    SectorId sectorId;

    SiteSectorAdded() {
    }

    public SiteSectorAdded(SiteId id, SectorId sectorId) {
        super(id);
        this.sectorId = sectorId;
    }

    public SectorId getSectorId() {
        return sectorId;
    }
}
