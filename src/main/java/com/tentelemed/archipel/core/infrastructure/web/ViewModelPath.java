package com.tentelemed.archipel.core.infrastructure.web;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 16/01/14
 * Time: 17:45
 */
public class ViewModelPath {
    String path;
    BaseViewModel model;
    List<Runnable> listeners = new ArrayList<>();
    Object lastValue;

    public ViewModelPath(BaseViewModel model, String path) {
        this.model = model;
        this.path = path;
    }

    public void addListener(Runnable r) {
        if (r == null) return;
        if (! listeners.contains(r)) {
            listeners.add(r);
        }
    }


    public List<Runnable> getListeners() {
        return Collections.unmodifiableList(listeners);
    }

    public void removeListener(Runnable r) {
        this.listeners.remove(r);
    }

    public void clearListeners() {
        this.listeners.clear();
    }

    public String getPath() {
        return path;
    }

    public BaseViewModel getModel() {
        return model;
    }

    public Object getLastValue() {
        return lastValue;
    }

    public void setLastValue(Object value) {
        this.lastValue = value;
    }

    public Object getCurrentValue() {
        try {
            return PropertyUtils.getNestedProperty(model, path);
        } catch (NestedNullException e) {
            // ce cas est normal
            return null;
        } catch (InvocationTargetException e) {
            Throwable t = e.getTargetException();
            if (t instanceof RuntimeException) throw ((RuntimeException)t);
            throw new RuntimeException(t);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
