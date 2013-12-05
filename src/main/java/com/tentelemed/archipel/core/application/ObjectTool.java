package com.tentelemed.archipel.core.application;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 05/12/13
 * Time: 10:26
 */
public class ObjectTool {
    private final static Logger log = LoggerFactory.getLogger(ObjectTool.class);

    /**
     * retourne la 'distance' entre une classe fille et un classe parente, en nombre
     * de classes intermédiaires
     *
     * @return 0 si la classe courante implemente l'interface, 1 si elle etend la classe, 2+
     */
    public static <U, V> int getInheritanceDistance(Class<V> child, Class<U> parent) {
        if (!parent.isAssignableFrom(child)) return 100;
        List<Class> toTest = new ArrayList<Class>();
        toTest.add(child);
        return _getInheritanceSteps(toTest, parent);
    }

    private static int _getInheritanceSteps(List<Class> childs, Class parent) {
        if (childs == null || childs.size() == 0) return 100; // en fait la classe n'implemente pas
        for (Class child : childs) {
            if (child.equals(parent)) {
                return 0;
            }
        }
        List<Class> toTest = new ArrayList<Class>();
        for (Class child : childs) {
            if (child.getSuperclass() != null) toTest.add(child.getSuperclass());
            toTest.addAll(Arrays.asList(child.getInterfaces()));
        }
        return 1 + _getInheritanceSteps(toTest, parent);
    }

    public static String serializeToString(Object value) {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(bout);
            oout.writeObject(value);
            oout.close();
            return new String(Base64.encode(bout.toByteArray()));
        } catch (Exception e) {
            log.error(null, e);
            return null;
        }
    }

    public static Object deSerializeFromString(String string) {
        try {
            byte[] encoded = string.getBytes();
            byte[] decoded = Base64.decode(encoded);
            ByteArrayInputStream bin = new ByteArrayInputStream(decoded);
            ObjectInputStream oin = new ObjectInputStream(bin);
            return oin.readObject();
        } catch (Exception e) {
            log.error(null, e);
            return null;
        }
    }

    public static Method findMethodByProperty(Object entity, String propertyName) {
        return findMethodByProperty(entity.getClass(), propertyName);
    }

    private static Map<Class, Map<String, Method>> entityMethods = new HashMap<>();

    public static Method findMethodByProperty(Class realClass, String propertyName) {
        Map<String, Method> methods = entityMethods.get(realClass);
        if (methods == null) {
            methods = new HashMap<>();
            entityMethods.put(realClass, methods);
        }
        Method m = methods.get(propertyName);
        if (m == null) {
            m = ObjectTool._findMethodByProperty(realClass, propertyName);
            methods.put(propertyName, m);
        }
        return m;
    }

    private static Method _findMethodByProperty(Class c, String s) {
        try {
            StringTokenizer st = new StringTokenizer(s, ".");
            Method method = null;
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                PropertyDescriptor[] descs = PropertyUtils.getPropertyDescriptors(c);
                boolean found = false;
                for (PropertyDescriptor desc : descs) {
                    if (desc.getName().equals(token)) {
                        method = desc.getReadMethod();
                        if (method != null) {
                            c = method.getReturnType();
                            found = true;
                            break;
                        }
                    }
                }
                // si on n'a pas trouvé, elle n'existe pas
                if (!found) {
                    return null;
                }
            }
            return method;
        } catch (Exception e) {
            log.error(null, e);
        }
        return null;
    }

    public static Object getProperty(Object entity, String propertyPath) {
        try {
            return PropertyUtils.getProperty(entity, propertyPath);
        } catch (NestedNullException e) {
            // ras
            return null;
        } catch (Exception e) {
            log.error(null, e);
            return null;
        }
    }

    public static Class classForName(String value) {
        try {
            return Class.forName(value);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
