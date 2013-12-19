package com.tentelemed.archipel.site.domain.event;


import com.tentelemed.archipel.site.domain.model.SiteId;
import com.tentelemed.archipel.site.domain.model.SiteType;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class SiteMainInfoUpdated extends SiteDomainEvent {

    private SiteType type;
    private String name;
    private String ident;

    SiteMainInfoUpdated() {
    }

    public SiteMainInfoUpdated(SiteId id, SiteType type, String name, String ident) {
        super(id);
        this.type = type;
        this.name = name;
        this.ident = ident;
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
}
