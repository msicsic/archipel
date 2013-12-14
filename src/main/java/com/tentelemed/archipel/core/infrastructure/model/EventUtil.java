package com.tentelemed.archipel.core.infrastructure.model;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.domain.model.EntityId;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
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
        Class currentClass = event.getClass();

        // un event ne doit jamais avoir un id null
        if (event.getId() == null) {
            throw new RuntimeException("Event must have non null ID : "+event.getClass().getSimpleName());
        }

        try {
            Method method = entity.getClass().getMethod("applyEvent", event.getClass());
            method.invoke(entity, event);
        } catch (Exception e2) {
            while (currentClass != null) {
                for (Field field : currentClass.getDeclaredFields()) {
                    try {
                        int mod = field.getModifiers();
                        if (! Modifier.isTransient(mod)) {
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
                        throw new RuntimeException("Can't copy field '" + field.getName()+"', aggregate : "+entity.getClass().getSimpleName()+", event : "+event.getClass().getSimpleName());
                    }
                }
                currentClass = currentClass.getSuperclass();
            }
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
