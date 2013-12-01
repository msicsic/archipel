package com.tentelemed.archipel.gam.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/10/13
 * Time: 10:42
 */
@Component
@Scope("prototype")
public class UiGamViewModel extends BaseViewModel {

    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
