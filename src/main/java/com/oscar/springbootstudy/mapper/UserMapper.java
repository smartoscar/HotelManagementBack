package com.oscar.springbootstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oscar.springbootstudy.pojo.User;
import com.oscar.springbootstudy.pojo.dto.UserDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User> {
    List<UserDto> selectUserPage(Page page, @Param("param") UserDto userDto);

    Integer updatePassword(Map<String, Object> params);

    UserDto getUserDtoByName(@Param("username") String username);
}
