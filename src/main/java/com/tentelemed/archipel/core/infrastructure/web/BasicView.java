package com.tentelemed.archipel.core.infrastructure.web;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.data.util.NestedMethodProperty;
import com.vaadin.data.util.NestedMethodProperty2;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.xpoft.vaadin.VaadinMessageSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/10/13
 * Time: 14:25
 */
public abstract class BasicView<M extends BasicViewModel> extends CustomComponent implements View {

    @Autowired
    protected VaadinMessageSource msg;

    protected abstract M getModel();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    protected void showError(Throwable e) {
        new Notification(e.getMessage(), Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
    }

    protected BeanFieldGroup<? extends BasicViewModel> getBinder() {
        return getModel().getBinder();
    }

    protected Label bind(Label field, String path) {
        getModel().getItem().addNestedProperty(path);
        field.setPropertyDataSource(getModel().getItem().getItemProperty(path));
        field.setImmediate(true);
        return field;
    }

    protected <C extends Field> C bind(C field, String path) {

        getBinder().bind(field, path);

        if (field instanceof FieldEvents.BlurNotifier) {
            ((FieldEvents.BlurNotifier)field).addBlurListener(new FieldEvents.BlurListener() {
                @Override
                public void blur(FieldEvents.BlurEvent event) {
                    // nothing special to do, but this empty listener is needed to activate validation on focus lost
                }
            });
        }

        if (field instanceof AbstractTextField) {
            ((AbstractTextField) field).setNullRepresentation("");
        }

        return field;
    }

    protected void refreshUI() {
        for (Object os : getModel().getItem().getItemPropertyIds()) {
            String s = os.toString();
            Property prop = getModel().getItem().getItemProperty(s);
            if (prop instanceof MethodProperty) {
                ((MethodProperty)prop).fireValueChange();
            } else if (prop instanceof NestedMethodProperty2) {
                ((NestedMethodProperty2)prop).fireValueChange();
            }
        }
        onRefresh();
    }

    /**
     * Listen refresh UI events.
     */
    protected void onRefresh() {}

    private Class<M> getModelClass() {
        return (Class<M>) getModel().getClass();
    }

    protected <U extends AbstractField> U bindSimple(U field, String path) {
        //getItem().addNestedProperty(path);
        field.setPropertyDataSource(getModel().getItem().getItemProperty(path));
        field.addValidator(new BeanValidator(getModelClass(), path));
        field.setImmediate(true);
        //itemProps.add(path);
        return field;
    }

    protected Button bind(Button button, final String path) {
        button.addClickListener(new Button.ClickListener() {
            @Override public void buttonClick(Button.ClickEvent event) {
                call(path);
            }
        });
        return button;
    }

    protected void call(String path) {
        try {
            Method m = getModel().getClass().getMethod("action_" + path);
            m.invoke(getModel());
        } catch (InvocationTargetException e) {
            showError(e.getTargetException());
        } catch (Exception e) {
            showError(e);
        } finally {
            refreshUI();
        }
    }

    protected String getText(String key) {
        return msg.getMessage(getModel().getClass().getSimpleName()+"."+key);
    }

    protected String gt(String key) {
        return getText(key);
    }
}
