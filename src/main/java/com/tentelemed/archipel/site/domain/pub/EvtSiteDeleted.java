package com.tentelemed.archipel.site.domain.pub;


/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class EvtSiteDeleted extends EvtSiteDomainEvent {
    EvtSiteDeleted() {
    }

    @Override
    public Type getCrudType() {
        return Type.DELETE;
    }

    public EvtSiteDeleted(SiteId id) {
        super(id);
    }

}
