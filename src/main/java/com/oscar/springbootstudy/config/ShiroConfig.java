package com.oscar.springbootstudy.config;

import com.oscar.springbootstudy.base.OsRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

//@Configuration
public class ShiroConfig {

    @Autowired
    private OsRealm osRealm;

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(CookieRememberMeManager rememberMeManager){
        // 1.创建DefaultWebSecurityManager
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 2.创建加密对象，设置相关属性
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 2.1.采用MD5加密
        matcher.setHashAlgorithmName("md5");
        // 2.2.迭代加密次数
        matcher.setHashIterations(3);
        // 3.将加密对象存储刀OsRealm中
        osRealm.setCredentialsMatcher(matcher);
        // 4.将OsRealm存入DefaultWebSecurityManager中
        defaultWebSecurityManager.setRealm(osRealm);
        // 5.记住我rememberMe选项
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager);
        return defaultWebSecurityManager;
    }

    // cookie设置
    @Bean
    public SimpleCookie rememberMeCookie(){
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 设置跨域
        // cookie.setDomain();
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30*24*60*80);
        return cookie;

    }

    // 创建shiro的cookie管理对象
    @Bean
    public CookieRememberMeManager rememberMeManager(SimpleCookie rememberMeCookie) {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie);
        cookieRememberMeManager.setCipherKey("123".getBytes());
        return cookieRememberMeManager;
    }

    // 配置Shiro内置过滤器拦截范围
    @Bean
    public DefaultShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        // 设置不认证可以访问的资源
        definition.addPathDefinition("/","anon");
        definition.addPathDefinition("/Admin/login","anon");
        // 登出过滤
        definition.addPathDefinition("/Admin/logout", "logout");
        //设置需要进行登陆认证的拦截范围
        definition.addPathDefinition("/**", "authc");
        // rememberMe拦截
        definition.addPathDefinition("/**", "user");
        return definition;
    }

    // 如果使用thymeleaf的shiro标签 需要引入thymeleaf-extras-shiro依赖，并配置ShiroDialect
}
