package com.tentelemed.archipel.core.infrastructure.model;

import com.tentelemed.archipel.core.domain.model.EntityId;
import com.tentelemed.archipel.core.domain.pub.DomainEvent;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 05/12/13
 * Time: 19:13
 */
public class EventUtil {

    public static void applyEvent(Object entity, DomainEvent event) {
        applyEvent(entity, event, false);
    }

    public static void autoApply(Object entity, DomainEvent event) {
        Class currentClass = event.getClass();
        while (currentClass != null) {
            for (Field field : currentClass.getDeclaredFields()) {
                try {
                    int mod = field.getModifiers();
                    if (!Modifier.isTransient(mod)) {
                        field.setAccessible(true);
                        Object fieldValue = field.get(event);
                        if (fieldValue instanceof EntityId) {
                            // extraction de l'id sous forme String
                            fieldValue = ((EntityId) fieldValue).getId();
                        }
                        String fieldName = field.getName();

                        PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(entity, fieldName);
                        if (desc != null && desc.getWriteMethod() != null) {
                            // si un setter existe, on l'utilise
                            PropertyUtils.setProperty(entity, fieldName, fieldValue);

                        } else {
                            // sinon on utilise directement l'attribut
                            Field aggregateField = findField(entity, fieldName);
                            if (aggregateField != null) {
                                try {
                                    aggregateField.setAccessible(true);
                                    aggregateField.set(entity, fieldValue);
                                } finally {
                                    aggregateField.setAccessible(false);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Can't copy field '" + field.getName() + "', aggregate : " + entity.getClass().getSimpleName() + ", event : " + event.getClass().getSimpleName());
                }
            }
            currentClass = currentClass.getSuperclass();
        }
    }

    public static void applyEvent(Object entity, DomainEvent event, boolean allowAutomatic) {

        // un event ne doit jamais avoir un id null
        if (event.getId() == null) {
            throw new RuntimeException("Event must have non null ID : " + event.getClass().getSimpleName());
        }

        try {
            Method method = entity.getClass().getDeclaredMethod("handle", event.getClass());
            method.setAccessible(true);
            method.invoke(entity, event);
        } catch (InvocationTargetException e2) {
            Throwable t = e2.getTargetException();
            if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            }
            throw new RuntimeException("Error in '" + entity.getClass().getSimpleName() + ".handle(" + event.getClass().getSimpleName() + ")' method", e2.getTargetException());
        } catch (Exception e2) {
            if (!allowAutomatic) {
                throw new RuntimeException("No 'handle' method present in entity '" + entity.getClass().getSimpleName() + "' for event '" + event.getClass().getSimpleName() + "'");
            }
            autoApply(entity, event);
        }
    }

    private static Field findField(Object entity, String fieldName) {
        Class currentClass = entity.getClass();
        while (currentClass != null) {
            for (Field field : currentClass.getDeclaredFields()) {
                if (field.getName().equals(fieldName)) {
                    return field;
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return null;
    }
}
