package com.tentelemed.archipel.core.infrastructure.web;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.security.application.service.UserQueryService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.xpoft.vaadin.VaadinMessageSource;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/10/13
 * Time: 14:25
 */
public abstract class BaseView<M extends BaseViewModel> extends CustomComponent implements IView {
    protected static Logger log = LoggerFactory.getLogger(BaseView.class);
    BaseViewHelper<M> helper;
    @Autowired protected VaadinMessageSource msg;
    @Autowired protected UserQueryService userService;

    public void setModule(String name) {
        getModel().setModule(name);
    }

    public abstract M getModel();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    @Override
    public void onDisplay() {
    }

    boolean displayed;
    @Override
    public void setDisplayed() {
        displayed = true;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    @Override
    public void onClose() {
        getModel().onClose();
    }

    protected void showError(Throwable e) {
        helper.showError(e);
    }

    protected BeanFieldGroup<? extends BaseViewModel> getBinder() {
        return helper.getBinder();
    }

    protected BeanFieldGroup<? extends BaseViewModel> getUnbufferedBinder() {
        return helper.getUnbufferedBinder();
    }

    protected Label bind(Label field, String path) {
        return helper.bind(field, path);
    }

    protected <C extends Field> C bind(C field, String path) {
        return helper.bind(field, path);
    }

    protected <C extends Field> C bindFilter(C field, String path) {
        return helper.bind(field, path);
    }

    protected <C extends Field> C bind(final C field, String path, final boolean refresh, boolean buffered) {
        return helper.bind(field, path, refresh, buffered);
    }

    public void refreshUI() {
        helper.refreshUI();
    }

    /**
     * Listen refresh UI events.
     */
    public void onRefresh() {
    }

    private Class<M> getModelClass() {
        return (Class<M>) getModel().getClass();
    }

    protected <U extends AbstractField> U bindSimple(U field, String path) {
        return helper.bindSimple(field, path);
    }

    protected Button bind(Button button, final String path) {
        return helper.bind(button, path);
    }

    protected void call(String path) {
        helper.call(path);
    }

    protected String getText(String key) {
        return helper.getText(key);
    }

    protected String gt(String key) {
        return getText(key);
    }

    @PostConstruct
    private void _postConstruct() {
        helper = new BaseViewHelper(this, getModel(), msg);
        getModel().register(this);
        postConstruct();
    }

    public void postConstruct() {}

    public void onDomainEventReceived(DomainEvent event) {

    }

    protected boolean isPermitted(String action) {
        return userService.isPermitted(getModel().getModule()+":"+action);
    }
}
