package com.tentelemed.archipel.security.domain.pub;

import com.tentelemed.archipel.core.domain.model.EntityId;

import javax.persistence.Embeddable;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 21/10/13
 * Time: 16:36
 */
@Embeddable
public class UserId extends EntityId {
    public UserId() {
    }

    public UserId(Integer id) {
        super(id);
    }
}
