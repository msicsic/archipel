package com.tentelemed.archipel.core.application.service;

import com.google.common.base.Strings;
import com.google.common.eventbus.EventBus;
import com.tentelemed.archipel.core.application.EventRegistry;
import com.tentelemed.archipel.core.application.event.LogoutRequestEvent;
import com.tentelemed.archipel.core.domain.model.Module;
import com.tentelemed.archipel.core.infrastructure.web.ModuleRoot;
import com.tentelemed.archipel.core.infrastructure.web.RootView;
import com.tentelemed.archipel.site.domain.event.SiteDomainEvent;
import com.tentelemed.archipel.site.domain.event.RoomDomainEvent;
import com.tentelemed.archipel.site.domain.model.Room;
import com.tentelemed.archipel.site.domain.model.Site;
import com.tentelemed.archipel.site.infrastructure.model.SiteQ;
import com.tentelemed.archipel.site.infrastructure.model.RoomQ;
import com.tentelemed.archipel.security.application.event.RoleDomainEvent;
import com.tentelemed.archipel.security.application.event.UserDomainEvent;
import com.tentelemed.archipel.security.domain.model.Role;
import com.tentelemed.archipel.security.domain.model.User;
import com.tentelemed.archipel.security.infrastructure.model.RoleQ;
import com.tentelemed.archipel.security.infrastructure.model.UserQ;
import com.vaadin.ui.AbstractComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired EventRegistry eventRegistry;

    List<Module> allModules;

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

    private Map<String, List<Class>> _findListeners() {
        Map<String, List<Class>> result = new HashMap<>();
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(EventHandler.class));
        Set<BeanDefinition> list = scanner.findCandidateComponents("com.tentelemed.archipel");
        for (BeanDefinition def : list) {
            try {
                // il ne faut pas enregistrer les Scope autre que singleton
                Class c = Class.forName(def.getBeanClassName());
                EventHandler listener = (EventHandler) c.getAnnotation(EventHandler.class);
                String busName = listener.value();
                List<Class> listeners = result.get(busName);
                if (listeners == null) {
                    listeners = new ArrayList<>();
                    result.put(busName, listeners);
                }
                Scope scope = (Scope) c.getAnnotation(Scope.class);
                boolean dontAdd = false;
                if (scope != null) {
                    if (Strings.isNullOrEmpty(scope.value()) || scope.value().equals(ConfigurableBeanFactory.SCOPE_SINGLETON)) {
                    } else {
                        dontAdd = true;
                    }
                }
                if (!dontAdd) {
                    listeners.add(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private void _initEventRegistry() {
        eventRegistry.addEntry(UserDomainEvent.class, User.class, UserQ.class);
        eventRegistry.addEntry(RoleDomainEvent.class, Role.class, RoleQ.class);
        eventRegistry.addEntry(SiteDomainEvent.class, Site.class, SiteQ.class);
        eventRegistry.addEntry(RoomDomainEvent.class, Room.class, RoomQ.class);
    }

    public void initApplication() {
        _initEventRegistry();
        _findModules();
        for (Map.Entry<String, List<Class>> entry : _findListeners().entrySet()) {
            String busName = entry.getKey();
            EventBus bus = (EventBus) applicationContext.getBean("eventBus");
            if (!busName.equals(EventHandler.DEFAULT_BUS)) {
                bus = (EventBus) applicationContext.getBean(busName);
            }
            for (Class c : entry.getValue()) {
                try {
                    Object listener = applicationContext.getBean(c);
                    bus.register(listener);
                } catch (BeansException e) {
                    log.error(null, e);
                }
            }
        }
    }

    public void logout() {
        fire(new LogoutRequestEvent());
    }
}
