package com.tentelemed.archipel.security.infrastructure.persistence.domain;

import com.tentelemed.archipel.core.infrastructure.domain.BaseEntityQ;
import com.tentelemed.archipel.security.domain.model.Right;
import com.tentelemed.archipel.security.domain.model.RoleId;
import com.tentelemed.archipel.security.domain.model.UserId;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 01/12/13
 * Time: 01:13
 */
@Entity
public class RoleQ extends BaseEntityQ<RoleId> {
    @NotNull @Size(min = 2, max = 50) String name;
    @NotNull @Size(min = 2, max = 20000) String rightString = null;
    @Transient transient Set<Right> rights = null;

    public RoleQ() {
    }

    @Override
    protected Class<RoleId> getIdClass() {
        return RoleId.class;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRightString() {
        if (rightString == null) {
            buildRightsString();
        }
        return rightString;
    }

    public Set<Right> getRights() {
        return Collections.unmodifiableSet(rights);
    }

    public void setRights(Set<Right> rights) {
        this.rights = rights;
        buildRightsString();
    }

    private void buildRightsString() {
        if (rights == null) return;
        String result = "";
        for (Right r : rights) {
            result = result + r.getName()+"/"+r.getValue()+" ";
        }
        rightString = result;
    }

    public void setRightString(String rightString) {
        this.rightString = rightString;
        this.rights = null;
    }


}
