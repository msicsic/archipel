package com.tentelemed.archipel.security.application.event;

import com.tentelemed.archipel.security.domain.model.UserId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:10
 */
public class EvtUserDeleted extends EvtUserDomainEvent {

    EvtUserDeleted() {
    }

    public EvtUserDeleted(UserId id) {
        super(id);
    }

    @Override
    public Type getCrudType() {
        return Type.DELETE;
    }
}
