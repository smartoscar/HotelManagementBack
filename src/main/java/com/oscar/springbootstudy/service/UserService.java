package com.oscar.springbootstudy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oscar.springbootstudy.base.BaseService;
import com.oscar.springbootstudy.commons.ApiData;
import com.oscar.springbootstudy.pojo.User;
import com.oscar.springbootstudy.pojo.dto.UserDto;

public interface UserService extends BaseService<User> {

    // 不含有角色信息
    User getUserByName(String username);

    ApiData login(String username, String password, boolean rememberMe);

    ApiData addUser(UserDto user);
    ApiData editUser(UserDto dto);

    IPage<UserDto> pageUsers(UserDto userDto);

    ApiData resetPassword(String oldPassword, String newPassword);

    // 含有角色信息
    UserDto getUserDtoByName(String username);

    boolean deleteUserById(Integer id);
}
