package com.tentelemed.archipel.core.infrastructure.config;

import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.annotation.WebListener;

/**
 * Pour init de Spring
 * <p/>
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 28/10/13
 * Time: 12:41
 */
@WebListener
public class BaseRequestContextListener extends RequestContextListener {
}
