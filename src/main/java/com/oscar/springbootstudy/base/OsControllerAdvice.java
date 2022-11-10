package com.oscar.springbootstudy.base;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.oscar.springbootstudy.commons.ApiData;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class OsControllerAdvice {

    @ResponseBody
    @ExceptionHandler({AuthorizationException.class, UnauthenticatedException.class, UnauthorizedException.class})
    public ApiData authorizationExceptionHandler(Exception e){
        e.printStackTrace();
        return new ApiData(ApiData.STATUS_FAIL, "您没有权限！");
    }
    @ResponseBody
    @ExceptionHandler({AuthenticationException.class, TokenExpiredException.class})
    public ApiData authenticationExceptionHandler(Exception e){
        e.printStackTrace();
        return new ApiData(ApiData.STATUS_FAIL, "用户名或密码错误(token无效或者与登录者不匹配)!");
    }
}
