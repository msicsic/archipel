package com.tentelemed.archipel.core.infrastructure.persistence;

import com.tentelemed.archipel.core.domain.model.EntityId;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.descriptor.sql.IntegerTypeDescriptor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class EntityIdType<M extends EntityId> implements UserType {

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        Integer value = (Integer) IntegerType.INSTANCE.get(rs, names[0], session);
        if (value == null)
            return null;
        else
            return createInstance(value);
    }

    protected abstract M createInstance(Integer id);

    @Override
    public abstract Class<M> returnedClass();

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null)
            IntegerType.INSTANCE.set(st, null, index, session);
        else
            IntegerType.INSTANCE.set(st, ((EntityId)value).getId(), index, session);
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{IntegerTypeDescriptor.INSTANCE.getSqlType()};
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}