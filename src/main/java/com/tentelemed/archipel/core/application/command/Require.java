package com.tentelemed.archipel.core.application.command;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 17/01/14
 * Time: 16:31
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Require {
    Class[] value();
}
