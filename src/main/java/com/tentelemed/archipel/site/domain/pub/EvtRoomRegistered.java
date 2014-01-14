package com.tentelemed.archipel.site.domain.pub;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 13:08
 */
public class EvtRoomRegistered extends EvtRoomDomainEvent {
    private String name;
    private boolean medical;
    private LocationPath locationPath;
    private SiteId siteId;

    EvtRoomRegistered() {
    }

    public EvtRoomRegistered(RoomId id, SiteId siteId, String name, boolean medical, LocationPath locationPath) {
        super(id);
        this.name = name;
        this.medical = medical;
        this.siteId = siteId;
        this.locationPath = locationPath;
    }

    @Override
    public Type getCrudType() {
        return Type.CREATE;
    }

    public SiteId getSiteId() {
        return siteId;
    }

    public String getName() {
        return name;
    }

    public boolean isMedical() {
        return medical;
    }

    public LocationPath getLocationPath() {
        return locationPath;
    }
}
