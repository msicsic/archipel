package com.tentelemed.archipel.core.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 04/11/13
 * Time: 14:58
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleRoot {
    /**
     * Identifiant du module
     * @return
     */
    String value();

    /**
     * Indique s'il est visible dans le menu
     * @return
     */
    boolean visible() default true;

    /**
     * Module principal qui héberge les autres (ecran principal)
     * @return
     */
    boolean root() default false;

    /**
     * Module permettant l'authentification
     * @return
     */
    boolean login() default false;
}
