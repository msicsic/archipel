package com.tentelemed.archipel.site.domain.pub;


/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class EvtSiteServiceAdded extends EvtSiteDomainEvent {
    String parent;
    String name;
    String code;

    public EvtSiteServiceAdded() {
    }

    public EvtSiteServiceAdded(SiteId id, String parent, String code, String name) {
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
