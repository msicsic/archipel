package com.tentelemed.archipel.core.infrastructure.web;

import com.google.common.eventbus.EventBus;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.xpoft.vaadin.VaadinMessageSource;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/10/13
 * Time: 10:53
 */
public class BasicViewModel {

    protected static final Logger log = LoggerFactory.getLogger(BasicViewModel.class);

    @Autowired
    EventBus eventBus;

    @Autowired
    protected VaadinMessageSource msg;

    BeanFieldGroup<? extends BasicViewModel> binder;
    NestingBeanItem item;
    boolean commited = false;
    boolean discarded = false;

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

    public BeanFieldGroup<BasicViewModel> getBinder() {
        if (binder == null) {
            binder = new BeanFieldGroup<>(getClass());
            //binder.setBuffered(false);
            binder.setItemDataSource(getItem());
        }
        return (BeanFieldGroup<BasicViewModel>) binder;
    }
}
