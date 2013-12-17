package com.tentelemed.archipel.core.domain.model;

import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 03/12/13
 * Time: 15:15
 */
public class MementoUtil {
    private final static Logger log = LoggerFactory.getLogger(MementoUtil.class);

    /**
     * Creation d'un Memento à partir d'un Aggregate ou DomainEvent
     *
     * @param model l'agregat à extraire
     * @return les données de l'agregat sous forme de Memento. Retourne également les sous objets
     */
    public static Memento createMemento(BuildingBlock model) {
        IdentityHashMap<Object, Object> done;
        done = new IdentityHashMap<>();
        try {
            return (Memento) _make(done, model);
        } catch (Exception e) {
            log.error(null, e);
            return null;
        }
    }

    private static Object _make(IdentityHashMap<Object, Object> done, Object object) throws Exception {
        if (object == null) {
            return null;
        }

        Object result = done.get(object);
        if (result != null) {
            return result;
        }

        if (object instanceof Collection) {
            Collection<Object> res = (Collection) object;
            if (object instanceof Set) {
                res = new HashSet<>();
            } else if (object instanceof List) {
                res = new ArrayList<>();
            }
            done.put(object, res);
            for (Object elem : (Collection) object) {
                res.add(_make(done, elem));
            }
            return res;

        } else if (object instanceof Map) {
            Map<Object, Object> res = new HashMap<>();
            done.put(object, res);
            for (Map.Entry entry : ((Map<Object, Object>) object).entrySet()) {
                res.put(_make(done, entry.getKey()), _make(done, entry.getValue()));
            }
            return res;

        } else if (object instanceof Object[]) {
            Object[] array = (Object[]) object;
            Object[] res = new Object[array.length];
            done.put(object, res);
            for (int i = 0; i < res.length; i++) {
                res[i] = _make(done, res[i]);
            }
            return res;

        } else if (object instanceof BuildingBlock) {
            BuildingBlock model = (BuildingBlock) object;
            Memento transport = new Memento(model.getClass());
            done.put(model, transport);
            Class current = model.getClass();
            while (current != null) {
                for (Field f : current.getDeclaredFields()) {
                    int mod = f.getModifiers();
                    if (!java.lang.reflect.Modifier.isStatic(mod) && !java.lang.reflect.Modifier.isTransient(mod)) {
                        try {
                            f.setAccessible(true);
                            try {
                                Object value = f.get(model);
                                value = _make(done, value);
                                transport.put(f.getName(), value);
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
            return transport;

        } else {
            //il n'est pas necessaire de polluer le set Done
            //done.put(new PMCapsuler(object), object);
            return object;
        }
    }

    /**
     * Creation d'un nouvel aggregate à partir d'un Memento
     *
     * @param memento
     * @return
     */
    public static BuildingBlock instanciateFromMemento(Memento memento) {
        IdentityHashMap<Object, Object> done;
        done = new IdentityHashMap<>();
        return (BuildingBlock) _unMake(done, memento, null);
    }

    public static void loadAggregate(BuildingBlock aggregate, Memento memento) {
        IdentityHashMap<Object, Object> done;
        done = new IdentityHashMap<>();
        _unMake(done, memento, aggregate);
    }

    private static Object _unMake(Map<Object, Object> done, Object object, BuildingBlock aggregate) {
        if (object == null) {
            return null;
        }

        Object result = done.get(object);
        if (result != null) {
            return result;
        }

        if (object instanceof Collection) {
            done.put(object, object);
            Collection coll = (Collection) object;
            List<Object> temp = new ArrayList<>();
            try {
                for (Object elem : coll) {
                    temp.add(_unMake(done, elem, null));
                }
            } catch (Exception e) {
                log.warn("Cannot instanciate aggregate from Memento", e);
                throw new RuntimeException(e);
            }
            coll.clear();
            coll.addAll(temp);

        } else if (object instanceof Map && !(object instanceof Memento)) {
            done.put(object, object);
            Map<Object, Object> map = (Map) object;
            Map<Object, Object> temp = new HashMap<>();
            for (Map.Entry entry : map.entrySet()) {
                temp.put(_unMake(done, entry.getKey(), null), _unMake(done, entry.getValue(), null));
            }
            map.clear();
            map.putAll(temp);

        } else if (object instanceof Object[]) {
            done.put(object, object);
            Object[] tab = (Object[]) object;
            for (int i = 0; i < tab.length; i++) {
                tab[i] = _unMake(done, tab[i], null);
            }

        } else if (object instanceof Memento) {
            Memento memento = (Memento) object;
            BuildingBlock instance;

            if (aggregate == null) {
                instance = memento.instanciate();
            } else {
                instance = aggregate;
            }

            done.put(object, instance);
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
                                Object value = _unMake(done, memento.get(prop), null);
                                field.set(instance, value);
                            } finally {
                                field.setAccessible(false);
                            }
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
            return instance;
        }
        return object;
    }

    public static String mementoToString(Memento memento) {
        XStream xstream = new XStream();
        xstream.setMode(XStream.ID_REFERENCES);
        xstream.alias("memento", Memento.class);
        //xstream.useAttributeFor(Memento.class, "type");
        return xstream.toXML(memento);
    }

    public static Memento mementoFromString(String string) {
        XStream xstream = new XStream();
        xstream.setMode(XStream.ID_REFERENCES);
        xstream.alias("memento", Memento.class);
        return (Memento) xstream.fromXML(string);
    }
}
