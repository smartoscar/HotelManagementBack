package com.oscar.springbootstudy.service;

import com.oscar.springbootstudy.base.BaseService;
import com.oscar.springbootstudy.commons.ApiData;
import com.oscar.springbootstudy.pojo.Resource;
import com.oscar.springbootstudy.pojo.Role;
import com.oscar.springbootstudy.pojo.RoleResource;

import java.util.List;

public interface RoleResourceService extends BaseService<RoleResource> {
    ApiData editRolePermission(Integer id, Integer[] ids);

    List<Resource> getRoleResourcesByRoleId(Integer id);
}
