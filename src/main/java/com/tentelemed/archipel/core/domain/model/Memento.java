package com.tentelemed.archipel.core.domain.model;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 03/12/13
 * Time: 14:17
 */
public class Memento extends HashMap<String, Object> {
    Class type;

    public Memento(Class type) {
        this.type = type;
    }

    public Class getType() {
        return type;
    }

    public BuildingBlock instanciate() {
        Constructor constructor = null;
        try {
            constructor = getType().getDeclaredConstructor();
            constructor.setAccessible(true);
            return (BuildingBlock) constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("You must declare a default constructor for Class : " + getType(), e);
        } finally {
            if (constructor != null) constructor.setAccessible(false);
        }
    }
}
