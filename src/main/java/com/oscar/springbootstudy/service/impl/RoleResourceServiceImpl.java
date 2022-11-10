package com.oscar.springbootstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oscar.springbootstudy.base.BaseServiceImpl;
import com.oscar.springbootstudy.commons.ApiData;
import com.oscar.springbootstudy.mapper.RoleResourceMapper;
import com.oscar.springbootstudy.pojo.Resource;
import com.oscar.springbootstudy.pojo.RoleResource;
import com.oscar.springbootstudy.service.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleResourceServiceImpl extends BaseServiceImpl<RoleResourceMapper, RoleResource> implements RoleResourceService {

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Override
    @Transactional
    public ApiData editRolePermission(Integer id, Integer[] ids) {
        List<RoleResource> list = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(id);
            roleResource.setResourceId(ids[i]);
            list.add(roleResource);
        }
        // 删除后再新增
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("role_id", id);
        remove(wrapper);
        saveBatch(list);
        return ApiData.success("修改成功");
    }

    @Override
    public List<Resource> getRoleResourcesByRoleId(Integer id) {
        return roleResourceMapper.getRoleResourcesByRoleId(id);
    }
}
