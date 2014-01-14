package com.tentelemed.archipel.security.infrastructure.model;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.tentelemed.archipel.core.infrastructure.model.BaseEntityQ;
import com.tentelemed.archipel.security.domain.pub.Right;
import com.tentelemed.archipel.security.domain.pub.RoleId;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
                List<String> values = Splitter.on("/").splitToList(r);
                Right right = new Right(values.get(0), values.get(1));
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
            result = result + r.getName() + "/" + r.getValue() + " ";
        }
        rightString = result;
    }

    public void setRightString(String rightString) {
        this.rightString = rightString;
        this.rights = null;
    }

}
