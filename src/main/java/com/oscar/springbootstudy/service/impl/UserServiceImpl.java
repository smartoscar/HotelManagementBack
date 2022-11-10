package com.oscar.springbootstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oscar.springbootstudy.base.BaseServiceImpl;
import com.oscar.springbootstudy.base.FastDfsClient;
import com.oscar.springbootstudy.commons.ApiData;
import com.oscar.springbootstudy.mapper.RoleMapper;
import com.oscar.springbootstudy.mapper.RoleResourceMapper;
import com.oscar.springbootstudy.mapper.UserMapper;
import com.oscar.springbootstudy.mapper.UserRoleMapper;
import com.oscar.springbootstudy.pojo.User;
import com.oscar.springbootstudy.pojo.dto.UserDto;
import com.oscar.springbootstudy.pojo.dto.UserResourceDto;
import com.oscar.springbootstudy.service.UserService;
import com.oscar.springbootstudy.tools.jwt.JwtToken;
import com.oscar.springbootstudy.tools.jwt.JwtUtil;
import com.oscar.springbootstudy.util.PermissionTree;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    @Autowired
    private FastDfsClient fastDfsClient;

    @Override
    public User getUserByName(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    // 传统shiro登录处理
//    @Override
//    public ApiData login(String username, String password, boolean rememberMe) {
//        // 1 获取subject对象
//        Subject subject = SecurityUtils.getSubject();
//        // 2 封装请求数据刀token
//        AuthenticationToken token = new UsernamePasswordToken(username, password, rememberMe);
//        // 3 调用login方法进行登陆认证
//        try {
//            subject.login(token);
//        } catch (UnknownAccountException e){
//            e.printStackTrace();
//            return new ApiData(ApiData.STATUS_FAIL, "用户名或密码不正确");
//        }  catch (IncorrectCredentialsException e){
//            e.printStackTrace();
//            return new ApiData(ApiData.STATUS_FAIL, "用户名或密码不正确");
//        } catch (AuthenticationException e){
//            e.printStackTrace();
//            return new ApiData(ApiData.STATUS_FAIL, "登陆失败");
//        }
//
//        return new ApiData(ApiData.STATUS_SUCCESS, "登陆成功");
//    }

    // 前后端分离shiro登录处理
    @Override
    public ApiData login(String username, String password, boolean rememberMe) {
        String tokenStr = JwtUtil.sign(username, password);
        JwtToken token = new JwtToken(tokenStr);
        Subject subject = SecurityUtils.getSubject();


        subject.login(token);
        System.out.println("认证成功");

        // 获取权限
        List<UserResourceDto> userResourceList = roleResourceMapper.getResourcesByUsername(username);
        // TODO 递归
        List<UserResourceDto> permissionTree = PermissionTree.getTree(userResourceList);

        Map<String, Object> resData = new HashMap<>();
        resData.put("token", tokenStr);
        // 放入权限
        resData.put("userPermission", permissionTree);
        return new ApiData(ApiData.STATUS_SUCCESS, "登录成功", resData);
    }

    @Transactional
    @Override
    public ApiData addUser(UserDto dto) {
        // TODO 设置成为登录用户
        String operator = "admin";
        // TODO 表单校验


        // 是否已存在用户
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username", dto.getUsername());
        if(count(wrapper) > 0) {
            return new ApiData(ApiData.STATUS_FAIL, "用户名已存在");
        }

        // 封装并插入新增的用户数据
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setNickname(dto.getNickname());
        user.setPassword(dto.getPassword());
        user.setPhoto(dto.getPhoto());
        user.setCreateUser(operator);
        int count = userMapper.insert(user);

        // 如有选择角色
        if(dto.getRoleId() != null && dto.getRoleId()>0) {
            // 如果角色存在，则增加用户与角色的映射
            if (roleMapper.selectById(dto.getRoleId()) != null) {
                // 插入用户角色映射
                Map<String, Object> params = Map.of("userId", user.getId(), "roleId", dto.getRoleId(), "createUser", operator);
                userRoleMapper.insertUserRole(params);
            } else {
                return new ApiData(ApiData.STATUS_FAIL, "角色不存在");
            }
        }

        if(count > 0){
            return new ApiData(ApiData.STATUS_SUCCESS, "添加成功");
        } else {
            return new ApiData(ApiData.STATUS_FAIL, "添加失败");
        }
    }

    @Override
    public ApiData editUser(UserDto dto) {
        // TODO 设置成为登录用户
        String operator = "admin";
        // TODO 表单校验
        if(dto == null || dto.getId() == null) {
            return new ApiData(ApiData.STATUS_FAIL, "用户id不能为空");
        }
        // 检查是否已存在其他用户使用该用户名
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username", dto.getUsername());
        wrapper.ne("id", dto.getId());
        if(count(wrapper) > 0) {
            return new ApiData(ApiData.STATUS_FAIL, "用户名已存在");
        }

        LocalDateTime nowTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        User user = new User();
        user.setId(dto.getId());
        user.setNickname(dto.getNickname());
        user.setPhoto(dto.getPhoto());
        //user.setPassword(JwtUtil.md5Encode(dto.getPassword(), dto.getUsername()));
        user.setUpdateUser(operator);
        user.setUpdateTime(nowTime);
        // 更新用户信息
        int count = userMapper.updateById(user);

        if(dto.getRoleId() != null && dto.getRoleId()>0) {
            // 如果角色存在，则修改用户与角色的映射
            if (roleMapper.selectById(dto.getRoleId()) != null) {
                // 更新用户角色映射
                Map<String, Object> params = Map.of("userId", dto.getId(),
                        "roleId", dto.getRoleId(),
                        "updateUser", operator);
                Integer userRoleId = userRoleMapper.updateUserRole(params);

                // 更新条数不存在，说明原来没有设置角色，则增加用户角色映射
                if(userRoleId == null) {
                    // 插入用户角色映射
                    params = Map.of("userId", user.getId(), "roleId", dto.getRoleId(), "createUser", operator);
                    userRoleMapper.insertUserRole(params);
                }
            } else {
                return new ApiData(ApiData.STATUS_FAIL, "角色不存在");
            }
        }

        if(count == 1){
            return new ApiData(ApiData.STATUS_SUCCESS, "修改成功");
        } else {
            return new ApiData(ApiData.STATUS_FAIL, "修改失败");
        }
    }

    @Override
    public IPage<UserDto> pageUsers(UserDto userDto) {
        Page<UserDto> page = new Page<>(userDto.getPageIndex(), userDto.getPageSize());
        List<UserDto> result = userMapper.selectUserPage(page, userDto);
        page.setRecords(result);
        return page;
    }

    @Override
    public ApiData resetPassword(String oldPassword, String newPassword) {
        // 获取登录用户的用户名
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        String loginUserName = JwtUtil.getUsername(token);
        // 判断旧密码是否输入正确
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username", loginUserName);
        wrapper.eq("password", oldPassword);
        if(count(wrapper) != 1){
            return new ApiData(ApiData.STATUS_FAIL, "输入的旧密码有误");
        }

        Map<String, Object> params = new HashMap<>();
        params.put("username", loginUserName);
        params.put("password", newPassword);
        if(userMapper.updatePassword(params) > 0) {
            return new ApiData(ApiData.STATUS_SUCCESS, "修改成功");
        } else {
            return new ApiData(ApiData.STATUS_FAIL, "修改失败");
        }
    }

    @Override
    public UserDto getUserDtoByName(String username) {
        return userMapper.getUserDtoByName(username);
    }

    @Override
    public boolean deleteUserById(Integer id) {
        User user = getById(id);
        if(removeById(id)) {
            // 如果有头像，则删除头像
            if(!StringUtils.isEmpty(user.getPhoto())) {
                fastDfsClient.deleteFile(user.getPhoto());
            }
            // 删除用户角色映射
            userRoleMapper.deleteUserRoleByUserId(id);
            return true;
        } else {
            return false;
        }
    }
}
