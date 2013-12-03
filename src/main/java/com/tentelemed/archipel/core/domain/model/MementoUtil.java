package com.tentelemed.archipel.core.domain.model;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 03/12/13
 * Time: 15:15
 */
public class MementoUtil {
    static Class[] types = {
            String.class,
            Integer.class, Float.class, Double.class,
            Date.class, BigDecimal.class, Boolean.class};

    private static boolean isSimpleType(Object value) {
        return Arrays.asList(types).contains(value.getClass());
    }

    public static Memento _createMemento(Class type, Object instance) {
        Memento result = new Memento(type);
        Class current = type;
        while (current != null) {
            for (Field f : current.getDeclaredFields()) {
                int mod = f.getModifiers();
                if (!java.lang.reflect.Modifier.isStatic(mod) && !java.lang.reflect.Modifier.isTransient(mod)) {
                    try {
                        f.setAccessible(true);
                        try {
                            Object value = f.get(instance);
                            //if (value != null && value instanceof BaseEntity) {
                            if (!isSimpleType(value)) {
                                value = _createMemento(f.getType(), value);
                            }
                            result.put(f.getName(), value);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Can't auto-create memento for class : " + current.getSimpleName());
                        }
                    } finally {
                        f.setAccessible(false);
                    }
                }
            }
            current = current.getSuperclass();
        }
        return result;
    }

    public static void _applyMemento(Memento memento, Object instance) {
        if (! memento.getType().equals(instance.getClass())) {
            throw new RuntimeException("Memento cannot be applyed to an instance of a different type");
        }
        for (String prop : memento.keySet()) {
            try {
                Field field;
                boolean found = false;
                Class currentClass = memento.getType();
                while (!found && currentClass != null) {
                    try {
                        field = currentClass.getDeclaredField(prop);
                        found = true;
                        try {
                            field.setAccessible(true);
                            Object value = memento.get(prop);
                            if (value != null && value instanceof Memento) {
                                Memento childMemento = (Memento) value;
                                BaseEntity child = (BaseEntity) childMemento.getType().newInstance();
                                _applyMemento(childMemento, child);
                                value = child;
                            }
                            field.set(instance, value);
                        } finally {
                            field.setAccessible(false);
                        }
                    } catch (InstantiationException e) {
                        throw new RuntimeException("Bad memento for class (3) : " + memento.getType().getSimpleName());
                    } catch (NoSuchFieldException e) {
                        currentClass = currentClass.getSuperclass();
                    }
                }
                if (!found) {
                    throw new RuntimeException("Bad memento for class (1) : " + memento.getType().getSimpleName());
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Bad memento for class (2) : " + memento.getType().getSimpleName());
            }

        }
    }


}
