package com.tentelemed.archipel.security.domain.pub;

import com.tentelemed.archipel.core.infrastructure.persistence.EntityIdType;
import com.tentelemed.archipel.security.domain.pub.RoleId;
import com.tentelemed.archipel.security.domain.pub.UserId;
import org.hibernate.annotations.TypeDef;

import javax.persistence.MappedSuperclass;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 21/01/14
 * Time: 10:44
 */
@MappedSuperclass
@TypeDef(defaultForType = UserId.class, typeClass = UserIdType.class)
public class UserIdType extends EntityIdType<UserId> {

    @Override
    protected UserId createInstance(Integer id) {
        return new UserId(id);
    }

    @Override
    public Class<UserId> returnedClass() {
        return UserId.class;
    }
}
