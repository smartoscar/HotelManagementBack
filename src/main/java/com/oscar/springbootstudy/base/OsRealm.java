package com.oscar.springbootstudy.base;

import com.oscar.springbootstudy.pojo.User;
import com.oscar.springbootstudy.service.ResourceService;
import com.oscar.springbootstudy.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

//@Component
public class OsRealm extends AuthorizingRealm {

    private String SALT = "salt";
    // 注入UserService
    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    private ResourceService resourceService;

    // 自定义授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 1 创建对象，封装
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 2 存储角色
//        info.addRole("admin");
        // 3 存储权限
        // 3.1 获取用户身份信息
        String username = principalCollection.getPrimaryPrincipal().toString();
        System.out.println(username);
        List<String> userResources = resourceService.getUserResourceByUsername(username);
        info.addStringPermissions(userResources);
        return info;
    }

    // 自定义登陆认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1.获取用户身份信息
        String username = authenticationToken.getPrincipal().toString();
        // 2.调用业务层获取用户信息（数据库）
        User user = userService.getUserByName(username);
        // 3.非空判断，将数据封装返回
        if (user != null) {
            AuthenticationInfo info = new SimpleAuthenticationInfo(
                    authenticationToken.getPrincipal(),
                    user.getPassword(),
                    ByteSource.Util.bytes(SALT),
                    authenticationToken.getPrincipal().toString()
            );
            return info;
        }
        return null;
    }
}
