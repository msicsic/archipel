package com.tentelemed.archipel.site.domain.event;


import com.tentelemed.archipel.site.domain.model.SiteId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class SiteDeleted extends SiteDomainEvent {
    SiteDeleted() {
    }

    @Override
    public Type getCrudType() {
        return Type.DELETE;
    }

    public SiteDeleted(SiteId id) {
        super(id);
    }

}
