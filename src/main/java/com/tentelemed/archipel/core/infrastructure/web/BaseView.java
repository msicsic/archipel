package com.tentelemed.archipel.core.infrastructure.web;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.data.util.NestedMethodProperty2;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.xpoft.vaadin.VaadinMessageSource;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/10/13
 * Time: 14:25
 */
public abstract class BaseView<M extends BaseViewModel> extends CustomComponent implements View {
    Logger log = LoggerFactory.getLogger(BaseView.class);

    @Autowired
    protected VaadinMessageSource msg;

    public abstract M getModel();

    public abstract void postConstruct();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    protected void showError(Throwable e) {
        new Notification(e.getMessage(), Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
    }

    protected BeanFieldGroup<? extends BaseViewModel> getBinder() {
        return getModel().getBinder();
    }

    protected BeanFieldGroup<? extends BaseViewModel> getUnbufferedBinder() {
        return getModel().getUnbufferedBinder();
    }

    protected Label bind(Label field, String path) {
        getModel().getItem().addNestedProperty(path);
        field.setPropertyDataSource(getModel().getItem().getItemProperty(path));
        field.setImmediate(true);
        return field;
    }

    protected <C extends Field> C bind(C field, String path) {
        return bind(field, path, false, true);
    }

    protected <C extends Field> C bindFilter(C field, String path) {
        return bind(field, path, true, false);
    }

    Field currentComponent = null;

    protected <C extends Field> C bind(final C field, String path, final boolean refresh, boolean buffered) {

        if (field instanceof AbstractComponent) {
            ((AbstractComponent) field).setImmediate(true);
        }

        if (buffered) {
            getBinder().bind(field, path);
            if (field instanceof FieldEvents.BlurNotifier) {
                ((FieldEvents.BlurNotifier) field).addBlurListener(new FieldEvents.BlurListener() {
                    @Override
                    public void blur(FieldEvents.BlurEvent event) {
                        // nothing special to do, but this empty listener is needed to activate validation on focus lost
                        System.err.println("on blur : " + event.getSource());
                    }
                });
            }
        } else {
            getUnbufferedBinder().bind(field, path);
            field.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    // refresh screen
                    if (refresh) {
                        if (field == currentComponent) {
                            return;
                        }
                        try {
                            currentComponent = field;
                            refreshUI();
                        } finally {
                            currentComponent = null;
                        }
                    }
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
                ((MethodProperty) prop).fireValueChange();
            } else if (prop instanceof NestedMethodProperty2) {
                ((NestedMethodProperty2) prop).fireValueChange();
            }
        }
        for (Map.Entry<String, Button> entry : boundedButtons.entrySet()) {
            String path = entry.getKey();
            Button button = entry.getValue();
            try {
                Method m = getModelClass().getMethod("is" + path.substring(0, 1).toUpperCase() + path.substring(1) + "Enabled");
                if (m != null) {
                    boolean value = (boolean) m.invoke(getModel());
                    button.setEnabled(value);
                }
            } catch (Exception e) {
                // ras
            }
            try {
                Method m = getModelClass().getMethod("is" + path.substring(0, 1).toUpperCase() + path.substring(1) + "Visible");
                if (m != null) {
                    boolean value = (boolean) m.invoke(getModel());
                    button.setVisible(value);
                }
            } catch (Exception e) {
                // ras
            }
        }
        getBinder().discard();
        onRefresh();
    }

    /**
     * Listen refresh UI events.
     */
    protected void onRefresh() {
    }

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

    Map<String, Button> boundedButtons = new HashMap<>();

    protected Button bind(Button button, final String path) {
        if (!checkActionMethod(path)) {
            throw new RuntimeException("Bad binding for button : " + path);
        }
        boundedButtons.put(path, button);
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                call(path);
            }
        });
        return button;
    }

    protected boolean checkActionMethod(String action) {
        try {
            getModel().getClass().getMethod("action_" + action);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    protected void call(String path) {
        try {
            Method m = getModel().getClass().getMethod("action_" + path);
            m.invoke(getModel());
        } catch (InvocationTargetException e) {
            getModel().show(e.getTargetException());
            log.error(null, e);
        } catch (Exception e) {
            getModel().show(e);
            log.error(null, e);
        } finally {
            refreshUI();
        }
    }

    protected String getText(String key) {
        return msg.getMessage(getModel().getClass().getSimpleName() + "." + key);
    }

    protected String gt(String key) {
        return getText(key);
    }

    @PostConstruct
    private void _postConstruct() {
        getModel().register(this);
    }

    protected void onDomainEventReceived(DomainEvent event) {

    }
}
