package com.tentelemed.archipel.site.domain.model;

import com.tentelemed.archipel.core.domain.model.BaseEntity;
import com.tentelemed.archipel.site.domain.pub.Location;
import com.tentelemed.archipel.site.domain.pub.LocationPath;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 13:26
 */
public class ActivityUnit extends BaseEntity implements Location {
    @NotNull String name;
    @NotNull @Size(min=3, max=3) String code;
    @NotNull FunctionalUnit parent;

    ActivityUnit() {
    }

    public ActivityUnit(FunctionalUnit parent, String name, String code) {
        this.name = name;
        this.code = code;
        this.parent = parent;
        this.parent.addActivityUnit(this);
        validate();
    }

    @Override
    public String getPrefix() {
        return "AU";
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isMedical() {
        return parent.isMedical();
    }

    @Override
    public Set<Location> getChildren() {
        return new HashSet<>();
    }

    @Override
    public Location getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityUnit)) return false;

        ActivityUnit that = (ActivityUnit) o;

        if (!code.equals(that.code)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public LocationPath getPath() {
        return new LocationPath(this);
    }
}
