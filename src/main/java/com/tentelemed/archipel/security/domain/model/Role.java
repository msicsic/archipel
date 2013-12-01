package com.tentelemed.archipel.security.domain.model;

import com.tentelemed.archipel.core.domain.model.BaseVO;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 01/12/13
 * Time: 02:01
 */
public class Role extends BaseVO {
    @NotNull private final List<Right> rights;

    public Role(List<Right> rights) {
        validate("rights", rights);
        this.rights = rights;
    }

    public List<Right> getRights() {
        return Collections.unmodifiableList(rights);
    }
}
