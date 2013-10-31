package com.tentelemed.archipel.module.security.config;

import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * Pour init de Shiro
 *
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 30/10/13
 * Time: 11:24
 */
@WebFilter(
        filterName="shiroFilter",
        value="/*",
        initParams = {@WebInitParam(name="targetFilterLifecycle", value="true")})
public class ShiroFilter extends DelegatingFilterProxy {
    public ShiroFilter() {
        System.err.println("hop");
    }
}
