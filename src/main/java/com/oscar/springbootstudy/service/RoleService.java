package com.oscar.springbootstudy.service;

import com.oscar.springbootstudy.base.BaseService;
import com.oscar.springbootstudy.commons.ApiData;
import com.oscar.springbootstudy.pojo.Role;

public interface RoleService extends BaseService<Role> {
    ApiData addRole(Role role);

    ApiData editRole(Role role);

    ApiData deleteRole(Integer id);
}
