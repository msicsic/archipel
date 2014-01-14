package com.tentelemed.archipel.site.domain.pub;

import com.tentelemed.archipel.site.infrastructure.model.LocationQ;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:34
 */
public class EvtSiteRegistered extends EvtSiteDomainEvent {

    protected SiteType type;
    protected String name;
    protected String ident;
    protected LocationQ defaultSector;

    EvtSiteRegistered() {
    }

    public EvtSiteRegistered(SiteId id, SiteType type, String name, String ident, LocationQ defaultSector) {
        super(id);
        this.type = type;
        this.name = name;
        this.ident = ident;
        this.defaultSector = defaultSector;
    }

    @Override
    public Type getCrudType() {
        return Type.CREATE;
    }

    public SiteType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getIdent() {
        return ident;
    }

    public LocationQ getDefaultSector() {
        return defaultSector;
    }
}
