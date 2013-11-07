package com.tentelemed.archipel.core.application.service;

import com.google.common.eventbus.EventBus;
import com.tentelemed.archipel.core.domain.model.Module;
import com.tentelemed.archipel.core.application.event.LogoutRequestEvent;
import com.tentelemed.archipel.core.infrastructure.web.ModuleRoot;
import com.tentelemed.archipel.core.infrastructure.web.RootView;
import com.vaadin.ui.AbstractComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 04/11/13
 * Time: 14:19
 */
@Service
public class CoreService extends BaseService {

    static final Logger log = LoggerFactory.getLogger(CoreService.class);

    @Autowired EventBus eventBus;
    @Autowired ApplicationContext applicationContext;

    List<Module> allModules;
    List<Class> allListeners;

    public List<Module> findNonRootModules() {
        List<Module> views = _findModules();
        List<Module> res = new ArrayList<>();
        for (Module module : views) {
            if (!module.isRoot() && !module.isLogin()) {
                res.add(module);
            }
        }
        return res;
    }

    public List<Module> findAllModules() {
        return _findModules();
    }

    public Module getModule(String moduleId) {
        List<Module> views = _findModules();
        for (Module module : views) {
            if (module.getName().equals(moduleId)) {
                return module;
            }
        }
        return null;
    }

    private List<Module> _findModules() {
        if (allModules == null) {
            allModules = new ArrayList<>();
            ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
            scanner.addIncludeFilter(new AnnotationTypeFilter(ModuleRoot.class));
            Set<BeanDefinition> list = scanner.findCandidateComponents("com.tentelemed.archipel");
            for (BeanDefinition def : list) {
                try {
                    Class c = Class.forName(def.getBeanClassName());
                    ModuleRoot vv = (ModuleRoot) c.getAnnotation(ModuleRoot.class);
                    if (AbstractComponent.class.isAssignableFrom(c)) {
                        String name = vv.value();
                        Module module = new Module(name, c);
                        module.setRoot(vv.root());
                        module.setLogin(vv.login());
                        if (!module.isRoot() || RootView.class.isAssignableFrom(module.getViewClass())) {
                            allModules.add(module);
                        } else {
                            log.warn("Root(main) module must extends RootView : " + c.getSimpleName());
                        }

                    } else {
                        log.warn("RootView must extends AbstractComponent : " + c.getSimpleName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return allModules;
    }

    private List<Class> _findListeners() {
        if (allListeners == null) {
            allListeners = new ArrayList<>();
            ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
            scanner.addIncludeFilter(new AnnotationTypeFilter(EventListener.class));
            Set<BeanDefinition> list = scanner.findCandidateComponents("com.tentelemed.archipel");
            for (BeanDefinition def : list) {
                try {
                    Class c = Class.forName(def.getBeanClassName());
                    allListeners.add(c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return allListeners;
    }


    public void initApplication() {
        _findModules();
        for (Class listenerClass :_findListeners()) {
            try {
                Object listener = applicationContext.getBean(listenerClass);
                eventBus.register(listener);
            } catch (Exception e) {
                log.error(null, e);
            }
        }
    }

    public void logout() {
        fire(new LogoutRequestEvent());
    }
}
