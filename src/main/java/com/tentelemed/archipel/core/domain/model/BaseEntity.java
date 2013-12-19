package com.tentelemed.archipel.core.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 11:01
 */
public abstract class BaseEntity implements BuildingBlock {
    protected final static Logger log = LoggerFactory.getLogger(BaseEntity.class);

    private transient Validator validator;

    protected Validator getValidator() {
        if (validator == null) {
            this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        }
        return this.validator;
    }

    protected <M> M validate(String property, M value) {
        try {
            Set violations = getValidator().validateValue(getClass(), property, value);
            if (!violations.isEmpty()) {
                for (Object oviolation : violations) {
                    ConstraintViolation violation = (ConstraintViolation) oviolation;
                    log.warn("constraint violation : " + getClass().getSimpleName() + "." + property + " " + violation.getMessage());
                }
                throw new ConstraintViolationException(violations);
            }
            return value;
        } catch (RuntimeException e) {
            throw e;
        }
    }

    protected void validate() {
        Set violations = getValidator().validate(this);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

    }
}
