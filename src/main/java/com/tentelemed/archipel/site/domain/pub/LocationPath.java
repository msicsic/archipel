package com.tentelemed.archipel.site.domain.pub;

import com.google.common.base.Splitter;
import com.tentelemed.archipel.core.domain.model.BaseVO;

import javax.validation.constraints.NotNull;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 09/01/14
 * Time: 16:04
 */
public class LocationPath extends BaseVO {
    @NotNull String value;

    LocationPath() {
    }

    public LocationPath(Location location) {
        String res = "";
        while (location != null) {
            String code = location.getPrefix() + ":" + location.getCode();
            location = location.getParent();
            res = (location != null ? "|" : "") + code + res;
        }
        value = res;
    }

    public LocationPath(String path) {
        List<String> values = Splitter.on("|").splitToList(path);
        for (String value : values) {
            assertThat(Splitter.on(":").splitToList(value).size(), equalTo(2));
        }
        this.value = path;
    }

    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocationPath)) return false;

        LocationPath that = (LocationPath) o;

        if (!value.equals(that.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
