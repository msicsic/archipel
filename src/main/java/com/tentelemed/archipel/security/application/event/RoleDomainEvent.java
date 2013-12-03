package com.tentelemed.archipel.security.application.event;

import com.tentelemed.archipel.core.application.event.AbstractDomainEvent;
import com.tentelemed.archipel.security.domain.model.RoleId;
import com.tentelemed.archipel.security.domain.model.UserId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 19/11/13
 * Time: 15:13
 */
public abstract class RoleDomainEvent extends AbstractDomainEvent<RoleId> {

    protected RoleDomainEvent(RoleId id) {
        super(id);
    }
}
