package com.tentelemed.archipel.site.domain.event;

import com.tentelemed.archipel.site.domain.model.Sector;
import com.tentelemed.archipel.site.domain.model.SiteId;
import com.tentelemed.archipel.site.domain.model.SiteType;
import com.tentelemed.archipel.site.infrastructure.model.LocationQ;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:34
 */
public class SiteRegistered extends SiteDomainEvent {

    protected SiteType type;
    protected String name;
    protected String ident;
    protected LocationQ defaultSector;

    SiteRegistered() {
    }

    public SiteRegistered(SiteId id, SiteType type, String name, String ident, LocationQ defaultSector) {
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
