package com.oscar.springbootstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oscar.springbootstudy.pojo.Role;
import com.oscar.springbootstudy.pojo.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface UserRoleMapper extends BaseMapper<UserRole> {
    Integer deleteUserRoleByUserId(@Param("userId") Integer userId);

    Integer insertUserRole(Map<String, Object> params);

    Integer updateUserRole(Map<String, Object> params);
}
