package com.lb.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.lb.shiro.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 日志打印
     */
    private final static Logger log = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * 处理拦截器资源
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        log.info("=======================Shiro拦截器注入开始==========================");
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilter.setSecurityManager(securityManager);
        //设置登录页面
        shiroFilter.setLoginUrl("/route/login1");
        // shiroFilter.setSuccessUrl("index");

        //未授权界面
        shiroFilter.setUnauthorizedUrl("/route/error");
        //拦截器
        // filterChainDefinitions拦截器map必须用：LinkedHashMap，因为它必须保证有序
        Map<String, String> filterMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接swagger-ui
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/swagger-ui.html/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/v2/**", "anon");
        /**静态资源*/
        // -- static resources
        filterMap.put("/statics/**", "anon");
        //filterMap.put("/**/*.html", "anon");
        //filterMap.put("/**/*.css", "anon");
        // filterMap.put("/**/*.js", "anon");
        //filterMap.put("/**/*.jpg", "anon");
        //filterMap.put("/login", "anon");
        //filterMap.put("/login1", "anon");

        //退出系统
        //filterMap.put("/user/logout", "logout");
        //filterMap.put("/login1", "logout");

        /*filterMap.put("/user/logout", "anon");*/

        // 需要认证才可以访问(表示test下面的所有页面都需要认证)
        filterMap.put("/class/**", "authc");
        filterMap.put("/course/**", "authc");
        filterMap.put("/student/**", "authc");
        filterMap.put("/teacher/**", "authc");
        filterMap.put("/admin/**", "authc");
        filterMap.put("/common/**", "authc");
        filterMap.put("/*", "authc");
        // filterMap.put("/**", "authc");
        // filterMap.put("/*.*", "authc");
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }

    /**
     * 安全管理器
     */
    @Bean
    public SecurityManager securityManager() {
        log.info("========================shiro已经加载======================");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        return securityManager;
    }


    /**
     * 配置自定义的权限登录
     */

    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        return userRealm;
    }

    /**
     * 整合 thymeleaf
     *
     * @return
     */
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
