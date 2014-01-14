package com.tentelemed.archipel.core.domain.pub;

import com.tentelemed.archipel.core.domain.model.BuildingBlock;
import com.tentelemed.archipel.core.domain.model.EntityId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/11/13
 * Time: 17:41
 */
public interface DomainEvent<M extends EntityId> extends BuildingBlock {
    enum Type {
        CREATE, UPDATE, DELETE
    }

    Type getCrudType();

    M getId();

    boolean isUpdate();

    boolean isDelete();

    boolean isCreate();
}
