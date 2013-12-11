package com.tentelemed.archipel.infrastructure.config;

import com.google.common.eventbus.EventBus;
import com.tentelemed.archipel.core.application.EventRegistry;
import com.tentelemed.archipel.core.application.EventStore;
import com.tentelemed.archipel.security.infrastructure.shiro.MyRealm;
import com.tentelemed.gam.domain.TestEventStore;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.xpoft.vaadin.VaadinMessageSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 23/10/13
 * Time: 12:09
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.tentelemed.archipel"})
@EnableTransactionManagement
@ComponentScan("com.tentelemed.archipel")
public class TestSpringConfiguration {

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public Realm myRealm() {
        return new MyRealm();
    }

    @Bean
    public EventStore eventStore() {
        return new TestEventStore();
    }

    @Bean
    public EventRegistry eventRegistry() {
        return new EventRegistry();
    }

    @Bean(name = "eventBus")
    public EventBus eventBus() {
        return new EventBus();
    }

    @Bean(name = "storeEventBus")
    public EventBus storeEventBus() {
        return new EventBus();
    }

    @Bean(name = "localBus")
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public EventBus sessionEventBus() {
        return new EventBus();
    }

    @Bean
    public VaadinMessageSource vaadinMessageSource() {
        return new VaadinMessageSource();
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource res = new ReloadableResourceBundleMessageSource();
        res.setBasename("classpath:/locales/messages");
        res.setFallbackToSystemLocale(false);
        return res;
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

}
