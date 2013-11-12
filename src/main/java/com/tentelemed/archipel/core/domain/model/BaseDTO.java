package com.tentelemed.archipel.core.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 12/11/13
 * Time: 17:33
 */
public abstract class BaseDTO<M extends EntityId> implements Serializable {
    protected final static Logger log = LoggerFactory.getLogger(BaseDTO.class);

    String id;
    transient M entityId;

    protected String getId() {
        return id;
    }

    protected void setId(String id) {
        this.id = id;
    }

    public M getEntityId() {
        try {
            if (entityId == null) {
                entityId = getIdClass().newInstance();
                entityId.setId(getId());
            }
            return entityId;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(null, e);
        }
        return null;
    }

    public void setEntityId(M id) {
        this.id = id.toString();
        this.entityId = null;
    }

    protected abstract Class<M> getIdClass();

}
