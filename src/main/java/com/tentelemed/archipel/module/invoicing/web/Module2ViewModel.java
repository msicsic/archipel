package com.tentelemed.archipel.module.invoicing.web;

import com.tentelemed.archipel.core.web.BasicViewModel;
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
public class Module2ViewModel extends BasicViewModel {
    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}