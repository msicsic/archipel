package com.tentelemed.archipel.core.infrastructure.model;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.domain.model.EntityId;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 05/12/13
 * Time: 19:13
 */
public class EntityQUtil {

    public static void applyEvent(Object entity, DomainEvent event) {
        Class currentClass = event.getClass();

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
                            PropertyUtils.setProperty(entity, fieldName, fieldValue);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("Can't copy field : " + field.getName());
                    }
                }
                currentClass = currentClass.getSuperclass();
            }
        }
    }
}
