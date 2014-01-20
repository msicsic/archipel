package com.tentelemed.archipel.core.infrastructure.web;

import com.tentelemed.archipel.core.application.command.CmdGroup;
import com.tentelemed.archipel.core.application.command.Require;
import com.tentelemed.archipel.core.application.command.RightException;
import com.tentelemed.archipel.core.domain.model.DomainException;
import com.tentelemed.archipel.security.application.service.RightManager;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.data.util.NestedMethodProperty2;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.xpoft.vaadin.VaadinMessageSource;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/10/13
 * Time: 14:25
 */
public class BaseViewHelper<M extends BaseViewModel> {
    static Logger log = LoggerFactory.getLogger(BaseView.class);
    M model;
    IView view;
    Map<String, Button> boundedButtons = new HashMap<>();
    Field currentComponent = null;
    VaadinMessageSource msg;

    public BaseViewHelper(IView view, M model, VaadinMessageSource msg) {
        this.view = view;
        this.model = model;
        this.msg = msg;
    }

    public boolean isRefreshing() {
        return refreshing;
    }

    void showError(Throwable e) {
        new Notification(e.getMessage(), Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
    }

    BeanFieldGroup<? extends BaseViewModel> getBinder() {
        return model.getBinder();
    }

    BeanFieldGroup<? extends BaseViewModel> getUnbufferedBinder() {
        return model.getUnbufferedBinder();
    }

    Label bind(Label field, String path) {
        if (field.getCaption() == null) {
            field.setCaption(field.getValue());
        }
        model.getItem().addNestedProperty(path);
        Property property = model.getItem().getItemProperty(path);
        if (boolean.class.isAssignableFrom(property.getType())) {
            // TODO : convertir en Yes/No
            //field.setConverter(new StringToBooleanConverter());
        }
        field.setPropertyDataSource(property);
        final Class type = model.getItem().getItemProperty(path).getType();
        if (type.isEnum()) {
            field.setConverter(new Converter<String, Object>() {
                @Override public Object convertToModel(String value, Class<?> targetType, Locale locale) throws ConversionException {
                    return value == null ? null : Enum.valueOf(type, value);
                }

                @Override public String convertToPresentation(Object value, Class<? extends String> targetType, Locale locale) throws ConversionException {
                    return value == null ? null : ((Enum) value).name();
                }

                @Override public Class<Object> getModelType() {
                    return type;
                }

                @Override public Class<String> getPresentationType() {
                    return String.class;
                }
            });
        }
        field.setImmediate(true);
        return field;
    }

    <C extends Field> C bind(C field, String path) {
        return bind(field, path, false, true);
    }

    protected <C extends Field> C bindFilter(C field, String path) {
        return bind(field, path, true, false);
    }

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
                    if (isRefreshing()) return;

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

    boolean refreshing = false;

    protected void refreshUI() {
        try {
            if (refreshing) {
                return;
            }
            refreshing = true;
            for (Object os : model.getItem().getItemPropertyIds()) {
                String s = os.toString();
                Property prop = model.getItem().getItemProperty(s);
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
                        boolean value = (boolean) m.invoke(model);
                        button.setEnabled(value);
                    }
                } catch (Exception e) {
                    // ras
                }
                try {
                    Method m = getModelClass().getMethod("is" + path.substring(0, 1).toUpperCase() + path.substring(1) + "Visible");
                    if (m != null) {
                        boolean value = (boolean) m.invoke(model);
                        button.setVisible(value);
                    }
                } catch (Exception e) {
                    // ras
                }
            }
            getBinder().discard();

            // notification des listeners de changement du modele
            model.checkModelUpdates();

            view.onRefresh();

        } finally {
            refreshing = false;
        }
    }

    private Class<M> getModelClass() {
        return (Class<M>) model.getClass();
    }

    protected <U extends AbstractField> U bindSimple(U field, String path) {
        //getItem().addNestedProperty(path);
        field.setPropertyDataSource(model.getItem().getItemProperty(path));
        field.addValidator(new BeanValidator(getModelClass(), path));
        field.setImmediate(true);
        //itemProps.add(path);
        return field;
    }

    Button bind(RightManager rightManager, Button button, final String path) {
        if (!checkActionMethod(path)) {
            throw new RuntimeException("Bad binding for button : " + path);
        }

        // recupe annot des droits
        try {
            Method m = model.getClass().getMethod("action_" + path);
            Require req = m.getAnnotation(Require.class);
            if (req != null) {
                boolean check = false;
                for (Class cmdClass : req.value()) {
                    CmdGroup group = (CmdGroup) cmdClass.getAnnotation(CmdGroup.class);
                    String str = group.module()+":"+group.aggregat()+":"+cmdClass.getSimpleName();
                    if (rightManager.isPermitted(str)) {
                        check = true;
                        break;
                    }
                }
                button.setVisible(check);
            }
        } catch (Exception e) {
            log.error(null, e);
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

    boolean checkActionMethod(String action) {
        try {
            model.getClass().getMethod("action_" + action);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    void call(String path) {
        try {
            Method m = model.getClass().getMethod("action_" + path);
            m.invoke(model);
        } catch (InvocationTargetException e) {
            Throwable t = e.getTargetException();
            if (t instanceof ConstraintViolationException) {
                model.show((ConstraintViolationException) t);
            } else if (t instanceof DomainException) {
                model.show((DomainException) t);
            } else if (t instanceof RightException) {
                model.show((RightException) t);
            } else {
                model.show(t);
                log.error(null, e);
            }
        } catch (Exception e) {
            model.show(e);
            log.error(null, e);
        } finally {
            refreshUI();
        }
    }

    String getText(String key) {
        return msg.getMessage(model.getClass().getSimpleName() + "." + key);
    }

}
