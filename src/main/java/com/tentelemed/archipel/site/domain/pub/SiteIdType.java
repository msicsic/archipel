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
@TypeDef(defaultForType = SiteId.class, typeClass = SiteIdType.class)
public class SiteIdType extends EntityIdType<SiteId> {

    @Override
    protected SiteId createInstance(Integer id) {
        return new SiteId(id);
    }

    @Override
    public Class<SiteId> returnedClass() {
        return SiteId.class;
    }
}
