package com.tentelemed.archipel.security.domain.model;

import com.tentelemed.archipel.core.domain.model.BaseVO;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 01/12/13
 * Time: 02:01
 */
public class Right extends BaseVO {
    @NotNull private final String id;

    public Right(String id) {
        validate("id", id);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
