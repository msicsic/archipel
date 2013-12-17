package com.tentelemed.archipel.core.domain.model;

import com.tentelemed.archipel.core.infrastructure.web.IView;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 04/11/13
 * Time: 15:17
 */
public class Module {
    Class<? extends IView> viewClass;
    String name;
    boolean root;
    boolean login;

    public Module(String name, Class<? extends IView> viewClass) {
        this.name = name;
        this.viewClass = viewClass;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public Class<? extends IView> getViewClass() {
        return viewClass;
    }

    public String getName() {
        return name;
    }
}
