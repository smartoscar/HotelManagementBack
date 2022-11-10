package com.oscar.springbootstudy.config;

import com.oscar.springbootstudy.base.OsVueRealm;
import com.oscar.springbootstudy.tools.jwt.JwtFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * 1.需要设置关闭shiro自带的session；
 * 2.需要设置我们自己的身份认证过滤器MyFilter类（后面会介绍），
 *   关于MyFilter类也是前后端分离使用token认证的核心，不在使用shiro自带的authc过滤器（传统项目session校验使用的就是authc过滤器）了；
 * 3.realm不要设置缓存，传统一体式项目我们使用的用户名和密码校验，因为一个用户的用户名和密码不会经常改变，所以我们可以设置缓存，
 * 改变密码后需要更新缓存，而前后端分离项目使用的是经常改变的token，所以认证不能设置缓存，而权限可以单独设置@cache缓存
 */
@Configuration
public class ShiroVueConfig {

    // 创建shiroFilter, 负责拦截所有请求
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        Map<String, Filter> filters = new HashMap<>();
        filters.put("auth", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filters);
        // 配置系统受限资源
        // 配置系统公共资源
        Map<String, String> map = new HashMap<>();
        map.put("/Admin/login", "anon");
        map.put("/fileTrans/uploadFile", "anon");
        map.put("/**", "auth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    // 创建shiro安全管理器
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(Realm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        // 关闭shiro自带的session
        DefaultSubjectDAO dao = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        dao.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        defaultWebSecurityManager.setSubjectDAO(dao);
        // 配置realm
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    // 自定义Realm
    @Bean
    public Realm realm(OsVueRealm osVueRealm) {
        osVueRealm.setCachingEnabled(false);
        osVueRealm.setAuthenticationCachingEnabled(true);
        osVueRealm.setAuthenticationCacheName("AuthenticationCache");
        osVueRealm.setAuthorizationCachingEnabled(true);
        osVueRealm.setAuthorizationCacheName("AuthorizationCache");

        return osVueRealm;
    }
}
