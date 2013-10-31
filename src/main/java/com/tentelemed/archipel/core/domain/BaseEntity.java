package com.tentelemed.archipel.core.domain;

import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 11:01
 */
@MappedSuperclass
public abstract class BaseEntity<B extends EntityId> {

    @Id
    @org.hibernate.annotations.GenericGenerator(
            name = "OidGen",
            strategy = "com.tentelemed.archipel.core.repo.IdGenerator",
            parameters = {
                    @Parameter(name = "table", value = "AC_HIBERNATE_UNIQUE_KEY"),
                    @Parameter(name = "column", value = "NEXT_HI"),
                    @Parameter(name = "max_lo", value = "100")
            }
    )

    @GeneratedValue(generator = "OidGen")
    String id;

    @Version
    private Long version;

    private transient B entityId;

    protected Class<B> idClass;

    public B getEntityId() {
        if (entityId == null) {
            try {
                entityId = idClass.newInstance();
                entityId.setId(id);
            } catch (InstantiationException e) {
                // todo
            } catch (IllegalAccessException e) {
                // todo
            }
        }
        return entityId;
    }


}
