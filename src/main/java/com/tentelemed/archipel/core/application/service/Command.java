package com.tentelemed.archipel.core.application.service;

import com.tentelemed.archipel.core.domain.model.EntityId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 13/12/13
 * Time: 17:29
 */
public abstract class Command<ID extends EntityId> {
    public ID id;

    protected Command() {
    }

    protected Command(ID id) {
        this.id = id;
    }

    //public RESULT result;
}
