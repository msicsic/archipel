package com.tentelemed.archipel.site.domain.pub;

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
@TypeDef(defaultForType = RoomId.class, typeClass = RoomIdType.class)
public class RoomIdType extends EntityIdType<RoomId> {

    @Override
    protected RoomId createInstance(Integer id) {
        return new RoomId(id);
    }

    @Override
    public Class<RoomId> returnedClass() {
        return RoomId.class;
    }
}
