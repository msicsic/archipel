package com.tentelemed.archipel.core.web;

import com.tentelemed.archipel.module.security.web.LoginViewModel;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.MethodProperty;
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

    Set<String> itemProps = new HashSet<>();
    BeanItem item;

    @Autowired
    protected VaadinMessageSource msg;

    protected abstract M getModel();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    protected void showError(Throwable e) {
        new Notification(e.getMessage(), Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
    }

    protected BeanItem<LoginViewModel> getItem() {
        if (item == null) {
            item = new BeanItem<>(getModel());
        }
        return item;
    }

    protected void refreshUI() {
        for (String s : itemProps) {
            Property prop = item.getItemProperty(s);
            if (prop instanceof MethodProperty) {
                ((MethodProperty)prop).fireValueChange();
            }
        }
        onRefresh();
    }

    /**
     * Listen refresh UI events.
     */
    protected void onRefresh() {}

    protected void bind(Label field, String path) {
        field.setPropertyDataSource(getItem().getItemProperty(path));
        field.setImmediate(true);
        itemProps.add(path);
    }

    protected void bind(AbstractField field, String path) {
        field.setPropertyDataSource(getItem().getItemProperty(path));
        field.addValidator(new BeanValidator(LoginViewModel.class, path));
        field.setImmediate(true);
        itemProps.add(path);
    }

    protected void bind(Button button, final String path) {
        button.addClickListener(new Button.ClickListener() {
            @Override public void buttonClick(Button.ClickEvent event) {
                call(path);
            }
        });
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
