package com.oscar.springbootstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oscar.springbootstudy.base.BaseServiceImpl;
import com.oscar.springbootstudy.commons.ApiData;
import com.oscar.springbootstudy.mapper.ResourceMapper;
import com.oscar.springbootstudy.pojo.Resource;
import com.oscar.springbootstudy.service.ResourceService;
import com.oscar.springbootstudy.service.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl extends BaseServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private RoleResourceService roleResourceService;

    @Override
    public List<String> getUserResourceByUsername(String username) {
        return resourceMapper.getUserResourceByUsername(username);
    }

    /**
     * @Description:
     * @author: os
     * @Date: 2022/11/2 12:13
     * @Param: 
     * @Return: 
     */
    @Override
    public ApiData delete(Integer id) {
        // 如果存在子权限，无法删除
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("parent_id", id);
        if(resourceMapper.selectCount(wrapper) > 0) {
            return ApiData.fail("存在子权限，无法删除");
        }

        // 删除角色所拥有的权限映射
        wrapper.clear();
        wrapper.eq("resource_id", id);
        roleResourceService.remove(wrapper);

        // 删除权限
        if (resourceMapper.deleteById(id) == 1) {
            return ApiData.success("删除成功");
        } else {
            return ApiData.fail("删除失败");
        }
    }
}
