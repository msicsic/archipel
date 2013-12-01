package com.tentelemed.archipel.core.domain.model;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 30/11/13
 * Time: 23:17
 */
public class BaseVO {
    protected Validator validator;

    protected Validator getValidator() {
        if (validator == null) {
            this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        }
        return this.validator;
    }

    protected void validate(String property, Object value) {
        getValidator().validateValue(getClass(), property, value);
    }
}
