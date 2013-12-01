package com.tentelemed.archipel.core.infrastructure.domain;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 01/12/13
 * Time: 01:11
 */
@MappedSuperclass
public class BaseEntityHb {
    @Id protected String id;

    public String getId() {
        return id;
    }
}
