package com.tentelemed.archipel.core.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 11:01
 */
@MappedSuperclass
public abstract class BaseEntity<B extends EntityId> {
    protected final static Logger log = LoggerFactory.getLogger(BaseEntity.class);

    @Id
    /*@org.hibernate.annotations.GenericGenerator(
            name = "OidGen",
            strategy = "com.tentelemed.archipel.core.infrastructure.repo.IdGenerator",
            parameters = {
                    @Parameter(name = "table", value = "AC_HIBERNATE_UNIQUE_KEY"),
                    @Parameter(name = "column", value = "NEXT_HI"),
                    @Parameter(name = "max_lo", value = "100")
            }
    )*/

    //@GeneratedValue(generator = "OidGen")
    protected String id;

    //@Version
    //private Long version;

    private transient B entityId;

    public B getEntityId() {
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

    protected abstract Class<B> getIdClass();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;

        BaseEntity that = (BaseEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
