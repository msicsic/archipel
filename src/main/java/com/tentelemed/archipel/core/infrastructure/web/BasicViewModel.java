package com.tentelemed.archipel.core.infrastructure.web;

import com.google.common.eventbus.EventBus;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
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

    protected BeanItem getItem() {
        if (item == null) {
            item = new NestingBeanItem(this);
        }
        return item;
    }

    protected void commit() throws FieldGroup.CommitException {
        getBinder().commit();
    }

    protected void discard() {
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

    public BeanFieldGroup<? extends BasicViewModel> getBinder() {
        if (binder == null) {
            binder = new BeanFieldGroup<>(getClass());
            //binder.setBuffered(false);
            binder.setItemDataSource(getItem());
        }
        return binder;
    }
}
