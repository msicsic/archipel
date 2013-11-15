package com.tentelemed.archipel.core.domain.model;

import org.hibernate.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
    @org.hibernate.annotations.GenericGenerator(
            name = "OidGen",
            strategy = "com.tentelemed.archipel.core.infrastructure.repo.IdGenerator",
            parameters = {
                    @Parameter(name = "table", value = "AC_HIBERNATE_UNIQUE_KEY"),
                    @Parameter(name = "column", value = "NEXT_HI"),
                    @Parameter(name = "max_lo", value = "100")
            }
    )

    @GeneratedValue(generator = "OidGen")
    protected String id;

    @Version
    private Long version;

    private transient B entityId;

    protected Class<B> idClass;

    public B getEntityId() {
        if (entityId == null) {
            try {
                entityId = idClass.newInstance();
                entityId.setId(id);
            } catch (InstantiationException | IllegalAccessException e) {
                log.error(null, e);
            }
        }
        return entityId;
    }


}
