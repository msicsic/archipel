package com.tentelemed.archipel.core.domain.model;

import org.apache.commons.beanutils.PropertyUtils;

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

    protected <M> M validate(String property, M value) {
        getValidator().validateValue(getClass(), property, value);
        /*try {
            PropertyUtils.setProperty(this, property, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
        return value;
    }
}
