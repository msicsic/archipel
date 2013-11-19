package com.tentelemed.archipel.security.application.event;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.security.domain.model.UserId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 19/11/13
 * Time: 15:13
 */
public abstract class UserDomainEvent implements DomainEvent<UserId> {
    UserId id;

    protected UserDomainEvent() {
    }

    protected UserDomainEvent(UserId id) {
        this.id = id;
    }

    @Override
    public UserId getAggregateId() {
        return id;
    }
}
