package com.tentelemed.archipel.medicalcenter.application.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 16/12/13
 * Time: 17:20
 */
public class BeanCreatorTest {
    Logger log = LoggerFactory.getLogger(BeanCreatorTest.class);

    @Test
    public void testCreateBean() throws Exception {
        TestIncompleteBean o = new TestIncompleteBean();
        o.name = "Paul";
        o.lastName = "Durand";
        o.old = true;

        Object res = BeanCreator.createBean(o);
        Method m = null;
        try {
            m = res.getClass().getMethod("getName");
        } catch (Exception e) {
            log.error(null, e);
        }
        assertThat(m, notNullValue());
    }
}
