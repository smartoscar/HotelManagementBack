package com.oscar.springbootstudy.base;

import com.oscar.springbootstudy.pojo.User;
import com.oscar.springbootstudy.service.ResourceService;
import com.oscar.springbootstudy.service.UserService;
import com.oscar.springbootstudy.tools.jwt.JwtToken;
import com.oscar.springbootstudy.tools.jwt.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OsVueRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private ResourceService resourceService;


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //也可以直接存储user对象，以便在程序中直接获取使用，但是AuthenticationInfo在存储的时候也需要做响应的处理
        String username = JwtUtil.getUsername(principalCollection.toString());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<String> permissions = resourceService.getUserResourceByUsername(username);
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getPrincipal();
        // 解密获得username，用于和数据库进行对比
        String username = null;
        try {
            //这里工具类没有处理空指针等异常这里处理一下(这里处理科学一些)
            username = JwtUtil.getUsername(token);
        } catch (Exception e) {
            throw new AuthenticationException("heard的token拼写错误或者值为空");
        }
        if (username == null) {
            System.out.println("token无效(空''或者null都不行!)");
            throw new AuthenticationException("token无效");
        }
        User user = userService.getUserByName(username);
        if (user == null) {
            System.out.println("用户不存在!)");
            throw new AuthenticationException("用户不存在!");
        }
        if (!JwtUtil.verify(token, username, user.getPassword())) {
            System.out.println("用户名或密码错误(token无效或者与登录者不匹配)!");
            throw new AuthenticationException("用户名或密码错误(token无效或者与登录者不匹配)!");
        }
        return new SimpleAuthenticationInfo(token, token, getName());
    }
}
