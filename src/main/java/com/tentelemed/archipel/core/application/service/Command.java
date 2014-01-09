package com.tentelemed.archipel.core.application.service;

import com.tentelemed.archipel.core.domain.model.EntityId;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 13/12/13
 * Time: 17:29
 */
public abstract class Command<ID extends EntityId> {
    public ID id;
    public transient Map<EntityId, Object> data;

    protected Command() {
    }

    protected Command(ID id) {
        this.id = id;
    }

    public void addData(EntityId id, Object value) {
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(id, value);
    }

    public Object getData(EntityId id) {
        if (data == null) return null;
        return data.get(id);
    }

    //public RESULT result;
}
