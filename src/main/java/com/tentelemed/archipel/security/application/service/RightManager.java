package com.tentelemed.archipel.security.application.service;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 17/01/14
 * Time: 17:11
 */
public interface RightManager {

    boolean isPermitted(String toTest);
}
