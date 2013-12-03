package com.tentelemed.archipel.core.domain.model;

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
}
