package com.tentelemed.archipel.core.infrastructure.web;

import com.google.common.base.Throwables;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tentelemed.archipel.core.application.BeanCreator;
import com.tentelemed.archipel.core.domain.model.DomainException;
import com.tentelemed.archipel.core.domain.pub.DomainEvent;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import ru.xpoft.vaadin.VaadinMessageSource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/10/13
 * Time: 10:53
 */
public abstract class BaseViewModel {

    protected static final Logger log = LoggerFactory.getLogger(BaseViewModel.class);

    private IView view;

    @Autowired ApplicationContext context;
    @Autowired @Qualifier("localBus") EventBus eventBus;
    @Autowired protected VaadinMessageSource msg;

    BeanFieldGroup<? extends BaseViewModel> binder;
    BeanFieldGroup<? extends BaseViewModel> unBufferedBinder;
    NestingBeanItem item;
    boolean commited = false;
    boolean discarded = false;
    String module;

    public void setModule(String name) {
        this.module = name;
    }

    @PostConstruct
    public void _postConstruct() {
        // register the model on the session EventBus
        eventBus.register(this);
    }

    @PreDestroy
    public void _preDestroy() {
        eventBus.unregister(this);
    }

    protected <M> M beanify(M o) {
        return BeanCreator.createBean(o);
    }

    public void onClose() {
        eventBus.unregister(this);
    }

    protected <M extends View> M getView(Class<M> c) {
        return context.getBean(c);
    }

    protected void show(BasePopup view) {
        view.setModule(module);
        if (!view.isDisplayed()) {
            view.onDisplay();
            view.setDisplayed();
        }
        view.refreshUI();
        UI.getCurrent().addWindow(view);
    }

    public void show(ConstraintViolationException e) {
        String msg = "";
        for (ConstraintViolation c : e.getConstraintViolations()) {
            msg += "- '" + c.getPropertyPath() + "' " + c.getMessage() + "\n";
        }
        Notification.show(msg, Notification.Type.WARNING_MESSAGE);
    }

    public void show(DomainException e) {
        String msg = e.getMessage();
        Notification.show(msg, Notification.Type.WARNING_MESSAGE);
    }

    public void show(Throwable t) {
        if (Page.getCurrent() != null) {
            String s = Throwables.getStackTraceAsString(t);
            Notification.show(s, Notification.Type.ERROR_MESSAGE);
        }
    }

    protected void show(String caption, com.vaadin.ui.Notification.Type type) {
        if (Page.getCurrent() != null) {
            Notification.show(caption, type);
        }
    }

    protected void show(String caption) {
        if (Page.getCurrent() != null) {
            Notification.show(caption);
        }
    }

    public BeanItem getItem() {
        if (item == null) {
            item = new NestingBeanItem(this);
        }
        return item;
    }

    // Test only
    public boolean isDiscarded() {
        return discarded;
    }

    // Test only
    public boolean isCommited() {
        return commited;
    }

    public void commit() throws FieldGroup.CommitException {
        commited = true;
        getBinder().commit();
    }

    protected void discard() {
        discarded = true;
        getBinder().discard();
    }

    protected void showView(String viewId) {
        eventBus.post(new NavigationEvent(viewId));
    }

    protected String getText(String key) {
        return msg.getMessage(getClass().getSimpleName() + "." + key);
    }

    protected String gt(String key) {
        return getText(key);
    }

    public BeanFieldGroup<BaseViewModel> getBinder() {
        if (binder == null) {
            binder = new BeanFieldGroup<>(getClass());
            binder.setItemDataSource(getItem());
        }
        return (BeanFieldGroup<BaseViewModel>) binder;
    }

    public BeanFieldGroup<BaseViewModel> getUnbufferedBinder() {
        if (unBufferedBinder == null) {
            unBufferedBinder = new BeanFieldGroup<>(getClass());
            unBufferedBinder.setBuffered(false);
            unBufferedBinder.setItemDataSource(getItem());
        }
        return (BeanFieldGroup<BaseViewModel>) unBufferedBinder;
    }

    @Subscribe
    public void handleEvent(DomainEvent event) {
        // le modele peut traiter l'evt ici
        onDomainEventReceived(event);

        // ensuite delegation a la vue
        view.onDomainEventReceived(event);
    }

    protected void close() {
        if (view instanceof BasePopup) {
            ((BasePopup) view).close();
        }
    }

    protected void onDomainEventReceived(DomainEvent event) {
    }

    public void register(IView view) {
        this.view = view;
    }

    protected void confirm(String title, String message, Runnable r) {
        if (UI.getCurrent() != null) {
            UI.getCurrent().addWindow(new ConfirmDialog(this, title, message, r));
        } else {
            // dans le cas des TU, on lance directement le runnable
            r.run();
        }
    }

    protected <M> List<M> sort(List<M> collection, Comparator<M> comp) {
        Collections.sort(collection, comp);
        return collection;
    }

    protected <M> List<M> sort(List<M> collection, final String property) {
        Collections.sort(collection, new Comparator<M>() {
            @Override
            public int compare(M o1, M o2) {
                try {
                    Object val1 = PropertyUtils.getProperty(o1, property);
                    Object val2 = PropertyUtils.getProperty(o2, property);
                    if (val1 == null) {
                        if (val2 == null) return 0;
                        else return 1;
                    }
                    if (val2 == null) {
                        return -1;
                    }

                    Comparable compa1 = o1.toString();
                    Comparable compa2 = o2.toString();
                    if (val1 instanceof Comparable && val2 instanceof Comparable<?>) {
                        compa1 = (Comparable) val1;
                        compa2 = (Comparable) val2;
                    }

                    return compa1.compareTo(compa2);
                } catch (Exception e) {
                    log.error(null, e);
                    return 0;
                }
            }
        });
        return collection;
    }

    public String getModule() {
        return module;
    }


}