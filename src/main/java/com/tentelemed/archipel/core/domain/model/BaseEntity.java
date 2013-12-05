package com.tentelemed.archipel.core.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Id;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 11:01
 */
public abstract class BaseEntity<B extends EntityId> implements BuildingBlock {
    protected final static Logger log = LoggerFactory.getLogger(BaseEntity.class);

    @Id
    protected String id;
    private Long version = 0L;

    private transient B entityId;
    private transient Validator validator;

    protected Validator getValidator() {
        if (validator == null) {
            this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        }
        return this.validator;
    }

    protected <M> M validate(String property, M value) {
        try {
            getValidator().validateValue(getClass(), property, value);
            return value;
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public B getEntityId() {
        if (entityId == null) {
            try {
                entityId = getIdClass().newInstance();
                entityId.setId(id);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Cannot instanciate entityId : \""+getIdClass().getSimpleName()+"\", please provide an empty constructor");
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

    public Long getVersion() {
        return version;
    }
}
