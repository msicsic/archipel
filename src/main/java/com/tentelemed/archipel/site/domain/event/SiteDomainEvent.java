package com.tentelemed.archipel.site.domain.event;

import com.tentelemed.archipel.core.application.event.AbstractDomainEvent;
import com.tentelemed.archipel.site.domain.model.SiteId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:35
 */
public class SiteDomainEvent extends AbstractDomainEvent<SiteId> {
    public SiteDomainEvent() {
    }

    public SiteDomainEvent(SiteId id) {
        super(id);
    }
}
