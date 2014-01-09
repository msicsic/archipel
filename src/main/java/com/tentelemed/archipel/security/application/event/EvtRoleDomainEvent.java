package com.tentelemed.archipel.security.application.event;

import com.tentelemed.archipel.core.application.event.AbstractDomainEvent;
import com.tentelemed.archipel.security.domain.model.RoleId;

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