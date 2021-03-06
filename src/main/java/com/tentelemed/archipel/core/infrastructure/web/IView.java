package com.tentelemed.archipel.core.infrastructure.web;

import com.tentelemed.archipel.core.domain.pub.DomainEvent;
import com.vaadin.navigator.View;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 17/12/13
 * Time: 15:17
 */
public interface IView<M extends BaseViewModel> extends View {

    void setModule(String name);

    M getModel();

    /**
     * Listen refresh UI events (a chaque appel de RefreshUI()
     */
    void onRefresh();

    /**
     * On first display only
     */
    void onDisplay();

    void setDisplayed();

    boolean isDisplayed();

    void onClose();

    void onDomainEventReceived(DomainEvent event);

    void refreshUI();
}
