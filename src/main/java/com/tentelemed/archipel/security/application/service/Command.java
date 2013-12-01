package com.tentelemed.archipel.security.application.service;

import com.tentelemed.archipel.core.domain.model.EntityId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/11/13
 * Time: 12:07
 */
public class Command<M extends EntityId> {
    M id;

    public Command(M id) {
        this.id = id;
    }

    public M getId() {
        return id;
    }

}
