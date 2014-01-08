package com.tentelemed.archipel.site.domain.model;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 13:22
 */
public interface Location {

    String getCode();

    String getName();

    boolean isMedical();

    Set<? extends Location> getChildren();

    Location getParent();
}
