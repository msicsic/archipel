package com.tentelemed.archipel.security.domain.pub;

import com.tentelemed.archipel.core.domain.pub.AbstractDomainEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 19/11/13
 * Time: 15:13
 */
public abstract class EvtRoleDomainEvent extends AbstractDomainEvent<RoleId> {

    protected EvtRoleDomainEvent() {
    }

    protected EvtRoleDomainEvent(RoleId id) {
        super(id);
    }
}
