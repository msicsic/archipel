package com.tentelemed.archipel.security.domain.pub;

import com.tentelemed.archipel.core.domain.model.BaseVO;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 01/12/13
 * Time: 02:01
 */
public enum Right {

    GLOBAL_ADMIN("*"),
    USERS_ADMIN("users:*"),
    USERS_VIEW("users:read"),
    SITES_ADMIN("sites:*"),
    SITES_VIEW("sites:read"),
    ;

    private String permissions;

    Right(String permissions) {
        this.permissions = permissions;
    }

    public String getPermissions() {
        return permissions;
    }
}
