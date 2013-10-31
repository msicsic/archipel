package com.tentelemed.archipel.core.web;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/10/13
 * Time: 15:33
 */
public class NavigationEvent extends ViewEvent {
    String viewId;

    public NavigationEvent(String viewId) {
        this.viewId = viewId;
    }

    public String getViewId() {
        return viewId;
    }
}
