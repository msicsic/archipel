package com.tentelemed.archipel.core.infrastructure.web;

import com.google.common.base.Throwables;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.xpoft.vaadin.VaadinMessageSource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/10/13
 * Time: 10:53
 */
public abstract class BaseViewModel {

    protected static final Logger log = LoggerFactory.getLogger(BaseViewModel.class);

    private BaseView view;

    @Autowired
    @Qualifier("localBus")
    EventBus eventBus;

    @Autowired
    protected VaadinMessageSource msg;

    BeanFieldGroup<? extends BaseViewModel> binder;
    BeanFieldGroup<? extends BaseViewModel> unBufferedBinder;
    NestingBeanItem item;
    boolean commited = false;
    boolean discarded = false;

    @PostConstruct
    public void _postConstruct() {
        // register the model on the session EventBus
        eventBus.register(this);
    }

    @PreDestroy
    public void _preDestroy() {
        eventBus.unregister(this);
    }

    protected void show(Throwable t) {
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
        return msg.getMessage(getClass().getSimpleName()+"."+key);
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

    protected void onDomainEventReceived(DomainEvent event) {
    }

    public void register(BaseView view) {
        this.view = view;
    }

    protected void confirm(String title, String message, Runnable r) {
        UI.getCurrent().addWindow(new ConfirmDialog(title, message, r));
    }


}