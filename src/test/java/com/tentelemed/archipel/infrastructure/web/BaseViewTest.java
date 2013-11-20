package com.tentelemed.archipel.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BaseView;
import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.core.infrastructure.web.NestingBeanItem;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.data.util.NestedMethodProperty2;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 13/11/13
 * Time: 11:49
 */
public abstract class BaseViewTest<M extends BaseView, MM extends BaseViewModel> {
    Logger log = LoggerFactory.getLogger(BaseViewTest.class);

    protected NestingBeanItem<BaseViewModel> item;
    protected BeanFieldGroup<BaseViewModel> binder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        item = new NestingBeanItem(getViewModel());
        binder = new BeanFieldGroup<>(BaseViewModel.class);
        binder.setItemDataSource(item);

        when(getViewModel().getBinder()).thenReturn(binder);
        when(getViewModel().getItem()).thenReturn(item);
    }

    protected abstract M getView();
    protected abstract MM getViewModel();

    //@Test
    public void thatBindingWorksWorks() {

        // given

        // when
        getView().postConstruct();

        // then
        for (Object id : item.getItemPropertyIds()) {
            Property prop = item.getItemProperty(id);
            if (prop instanceof MethodProperty || prop instanceof NestedMethodProperty2) {
                // vérifier que cette méthode existe bien
                try {
                    String propName = (String) id;
                    log.info("("+getClass().getSimpleName()+") Testing path : "+propName);
                    Method m = checkNestedProperty(getView().getModel().getClass(), propName);
                    assertNotNull("path not found in model : "+propName, m);
                } catch (Exception e) {
                    log.error(null, e);
                }
            }
        }
    }

    protected Method checkNestedProperty(Class c, String path) {
        try {
            StringTokenizer st = new StringTokenizer(path, ".");
            Method method = null;
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                PropertyDescriptor[] descs = PropertyUtils.getPropertyDescriptors(c);
                boolean found = false;
                for (PropertyDescriptor desc : descs) {
                    if (desc.getName().equals(token)) {
                        method = desc.getReadMethod();
                        if (method != null) {
                            c = method.getReturnType();
                            found = true;
                            break;
                        }
                    }
                }
                // si on n'a pas trouvé, elle n'existe pas
                if (!found) {
                    return null;
                }
            }
            return method;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
