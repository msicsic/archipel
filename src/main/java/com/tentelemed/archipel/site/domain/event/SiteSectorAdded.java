package com.tentelemed.archipel.site.domain.event;


import com.tentelemed.archipel.site.domain.model.Sector;
import com.tentelemed.archipel.site.domain.model.SiteId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class SiteSectorAdded extends SiteDomainEvent {
    Sector.Type sectorType;
    String sectorCode;
    String sectorName;

    SiteSectorAdded() {
    }

    public SiteSectorAdded(SiteId id, Sector.Type type, String sectorCode, String sectorName) {
        super(id);
        this.sectorType = type;
        this.sectorCode = sectorCode;
        this.sectorName = sectorName;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public String getSectorName() {
        return sectorName;
    }

    public Sector.Type getSectorType() {
        return sectorType;
    }
}
