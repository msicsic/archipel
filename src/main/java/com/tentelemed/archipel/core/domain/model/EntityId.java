package com.tentelemed.archipel.core.domain.model;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 21/10/13
 * Time: 16:41
 */
@MappedSuperclass
public class EntityId implements Serializable {
    String id;

    void setId(String id) {
        this.id = id;
    }
}
