package com.tentelemed.archipel.site.domain.event;


import com.tentelemed.archipel.site.domain.model.SiteId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class SiteActivityUnitAdded extends SiteDomainEvent {
    String parent;
    String name;
    String code;

    public SiteActivityUnitAdded() {
    }

    public SiteActivityUnitAdded(SiteId id, String parent, String code, String name) {
        super(id);
        this.parent = parent;
        this.name = name;
        this.code = code;
    }

    public String getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
