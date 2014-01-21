package com.tentelemed.archipel.security.domain.pub;

import com.tentelemed.archipel.core.infrastructure.persistence.EntityIdType;
import org.hibernate.annotations.TypeDef;

import javax.persistence.MappedSuperclass;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 21/01/14
 * Time: 10:44
 */
@MappedSuperclass
@TypeDef(defaultForType = RoleId.class, typeClass = RoleIdType.class)
public class RoleIdType extends EntityIdType<RoleId> {

    @Override
    protected RoleId createInstance(Integer id) {
        return new RoleId(id);
    }

    @Override
    public Class<RoleId> returnedClass() {
        return RoleId.class;
    }
}
