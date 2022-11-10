package com.oscar.springbootstudy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oscar.springbootstudy.commons.ApiData;
import com.oscar.springbootstudy.pojo.Resource;
import com.oscar.springbootstudy.pojo.Role;
import com.oscar.springbootstudy.pojo.RoleResource;
import com.oscar.springbootstudy.service.ResourceService;
import com.oscar.springbootstudy.service.RoleResourceService;
import com.oscar.springbootstudy.service.RoleService;
import com.oscar.springbootstudy.util.PermissionTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Consumer;

@RestController
@RequestMapping("/Role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleResourceService roleResourceService;
    @Autowired
    private ResourceService resourceService;

    @GetMapping("/list")
    public ApiData getRoles(){
        List<Role> list = roleService.list();
        return new ApiData(ApiData.STATUS_SUCCESS, "查询成功", list);
    }

    @PostMapping("/add")
    public ApiData addRole(@RequestBody Role role){
        return roleService.addRole(role);
    }

    @PostMapping("/edit")
    public ApiData editRole(@RequestBody Role role){
        return roleService.editRole(role);
    }

    @PostMapping("/delete/{id}")
    public ApiData deleteRole(@PathVariable Integer id){
        return roleService.deleteRole(id);
    }

    @GetMapping("/rolePermissionList/{id}")
    public ApiData rolePermissionList(@PathVariable Integer id) {
        // 获取全步权限
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("is_enable", 1);
        wrapper.orderBy(true, true, "parent_id", "id");
        List<Resource> resourceList = resourceService.list(wrapper);
        // 获取角色所拥有的权限
        List<Resource> roleResourceList = roleResourceService.getRoleResourcesByRoleId(id);
        // 包装成map
        Map<String, Object> map = new HashMap<>();
        map.put("resourceList", PermissionTree.getTreeEX(resourceList));
        map.put("roleResourceList", roleResourceList);
        return ApiData.success(map);
    }

    @PostMapping("/editRolePermission/{id}")
    public ApiData editRolePermission(@PathVariable Integer id, @RequestBody Integer[] ids) {
        return roleResourceService.editRolePermission(id, ids);
    }
}
