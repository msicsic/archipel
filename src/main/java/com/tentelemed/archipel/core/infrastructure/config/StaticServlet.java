package com.tentelemed.archipel.core.infrastructure.config;

import org.apache.catalina.servlets.DefaultServlet;

import javax.servlet.annotation.WebServlet;

/**
 * Sert le contenu statique
 *
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 28/10/13
 * Time: 13:16
 */
@WebServlet(value = "/static/*")
public class StaticServlet extends DefaultServlet {
}
