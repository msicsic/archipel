package com.tentelemed.archipel.core.infrastructure.config;

import com.google.common.eventbus.EventBus;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.tentelemed.archipel.core.application.EventStore;
import com.tentelemed.archipel.core.application.service.CommandServiceFactory;
import com.tentelemed.archipel.core.infrastructure.repo.EventStoreImpl;
import com.tentelemed.archipel.security.application.command.RoleCmdHandler;
import com.tentelemed.archipel.security.application.command.UserCmdHandler;
import com.tentelemed.archipel.security.infrastructure.shiro.MyRealm;
import com.tentelemed.archipel.site.application.command.RoomCmdHandler;
import com.tentelemed.archipel.site.application.command.SiteCmdHandler;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
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
public class SpringConfiguration {

    @Bean
    public CommandServiceFactory commandServiceFactory() {
        return new CommandServiceFactory();
    }

    @Bean
    public SiteCmdHandler siteCmdHandler() {
        return commandServiceFactory().create(SiteCmdHandler.class);
    }

    @Bean
    public RoomCmdHandler roomCmdHandler() {
        return commandServiceFactory().create(RoomCmdHandler.class);
    }

    @Bean
    public UserCmdHandler userCmdHandler() {
        return commandServiceFactory().create(UserCmdHandler.class);
    }

    @Bean
    public RoleCmdHandler roleCmdHandler() {
        return commandServiceFactory().create(RoleCmdHandler.class);
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /*@Bean
    @DependsOn("lifecycleBeanPostProcessor")
    DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor b = new AuthorizationAttributeSourceAdvisor();
        b.setSecurityManager(securityManager());
        return b;
    }  */

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        return new DefaultWebSecurityManager(myRealm());
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean b = new ShiroFilterFactoryBean();
        b.setSecurityManager(securityManager());
        b.setLoginUrl("/");
        b.setSuccessUrl("/");
        b.setUnauthorizedUrl("/error/");
        b.setFilterChainDefinitions(
                "/** = anon"
        );
        return b;
    }

    @Bean
    public Realm myRealm() {
        return new MyRealm();
    }

    @Bean
    public EventStore eventStore() {
        return new EventStoreImpl();
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
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .build();
        MysqlDataSource ds = new MysqlDataSource();
        ds.setDatabaseName("gemed");
        ds.setUser("root");
        ds.setPassword("root");

        //ds.set

        return ds;

    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
//        hibernateJpaVendorAdapter.setDatabase(Database.H2);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource);
        lef.setJpaVendorAdapter(jpaVendorAdapter);
        lef.setPackagesToScan(
                "com.tentelemed.archipel"
//                , "com.tentelemed.archipel.invoicing.infrastructure.model"
//                , "com.tentelemed.archipel.security.infrastructure.model"
//                , "com.tentelemed.archipel.site.infrastructure.model"
        );
        lef.getJpaPropertyMap().put("hibernate.ejb.naming_strategy", "com.tentelemed.archipel.core.infrastructure.config.NamingStrategy");
        lef.afterPropertiesSet();
        return lef.getObject();
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }
}
