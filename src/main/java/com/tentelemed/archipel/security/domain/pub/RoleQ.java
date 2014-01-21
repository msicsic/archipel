package com.tentelemed.archipel.security.domain.pub;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.tentelemed.archipel.core.domain.pub.BaseEntityQ;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
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
        if (rights == null) {
            rights = new HashSet<>();
            for (String r : Splitter.on(" ").split(rightString)) {
                if (Strings.isNullOrEmpty(r)) continue;
                Right right = Right.valueOf(r.trim());
                rights.add(right);
            }
        }
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
            result = result + r.name() + " ";
        }
        rightString = result;
    }

    public void setRightString(String rightString) {
        this.rightString = rightString;
        this.rights = null;
    }

}
