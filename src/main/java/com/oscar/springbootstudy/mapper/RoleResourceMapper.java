package com.oscar.springbootstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oscar.springbootstudy.pojo.Resource;
import com.oscar.springbootstudy.pojo.RoleResource;
import com.oscar.springbootstudy.pojo.dto.UserResourceDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleResourceMapper extends BaseMapper<RoleResource> {
    List<UserResourceDto> getResourcesByUsername(@Param("username") String username);

    List<Resource> getRoleResourcesByRoleId(@Param("roleId") Integer roleId);
}
