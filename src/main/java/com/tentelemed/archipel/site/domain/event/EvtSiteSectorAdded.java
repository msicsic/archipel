package com.tentelemed.archipel.site.domain.event;


import com.tentelemed.archipel.site.domain.model.Sector;
import com.tentelemed.archipel.site.domain.model.SiteId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class EvtSiteSectorAdded extends EvtSiteDomainEvent {
    Sector.Type sectorType;
    String sectorCode;
    String sectorName;

    EvtSiteSectorAdded() {
    }

    public EvtSiteSectorAdded(SiteId id, Sector.Type type, String sectorCode, String sectorName) {
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
