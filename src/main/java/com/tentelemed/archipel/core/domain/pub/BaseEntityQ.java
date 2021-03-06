package com.tentelemed.archipel.core.domain.pub;

import com.tentelemed.archipel.core.domain.model.EntityId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Classe mere pour les Entités de la partie Query de l'application
 * <p/>
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 01/12/13
 * Time: 01:11
 */
@MappedSuperclass
public abstract class BaseEntityQ<I extends EntityId> {
    protected final static Logger log = LoggerFactory.getLogger(BaseEntityQ.class);

    private transient I entityId;
    @Id protected Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    protected abstract Class<I> getIdClass();

    public I getEntityId() {
        if (entityId == null) {
            try {
                entityId = getIdClass().newInstance();
                entityId.setId(id);
            } catch (InstantiationException | IllegalAccessException e) {
                log.error(null, e);
            }
        }
        return entityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntityQ)) return false;

        BaseEntityQ that = (BaseEntityQ) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
