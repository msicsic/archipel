package com.tentelemed.archipel.site.domain.pub;

import com.tentelemed.archipel.core.domain.pub.AbstractDomainEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:35
 */
public class EvtSiteDomainEvent extends AbstractDomainEvent<SiteId> {
    public EvtSiteDomainEvent() {
    }

    public EvtSiteDomainEvent(SiteId id) {
        super(id);
    }
}
