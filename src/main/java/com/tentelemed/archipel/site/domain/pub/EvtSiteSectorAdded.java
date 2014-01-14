package com.tentelemed.archipel.site.domain.pub;


/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class EvtSiteSectorAdded extends EvtSiteDomainEvent {
    SectorType sectorType;
    String sectorCode;
    String sectorName;

    EvtSiteSectorAdded() {
    }

    public EvtSiteSectorAdded(SiteId id, SectorType type, String sectorCode, String sectorName) {
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

    public SectorType getSectorType() {
        return sectorType;
    }
}
