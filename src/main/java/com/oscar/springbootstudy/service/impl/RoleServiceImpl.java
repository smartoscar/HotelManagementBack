package com.oscar.springbootstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oscar.springbootstudy.base.BaseServiceImpl;
import com.oscar.springbootstudy.commons.ApiData;
import com.oscar.springbootstudy.mapper.RoleMapper;
import com.oscar.springbootstudy.mapper.UserRoleMapper;
import com.oscar.springbootstudy.pojo.Role;
import com.oscar.springbootstudy.pojo.UserRole;
import com.oscar.springbootstudy.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 添加角色
     * @param role
     * @return
     */
    @Override
    public ApiData addRole(Role role) {
        // todo 表单校验

        // todo 数据合法性校验
        // 角色名不能重复
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name", role.getRoleName());
        if(this.count(wrapper)>0) {
            return new ApiData(ApiData.STATUS_FAIL, "角色名不能重复");
        }
        // todo 需改为登录用户
        role.setCreateUser("admin");
        if(role.getParentId() == null) {
            role.setParentId(0);
        }
        if(roleMapper.insert(role) == 1) {
            return new ApiData(ApiData.STATUS_SUCCESS, "添加成功");
        } else {
            return new ApiData(ApiData.STATUS_FAIL, "添加失败");
        }
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @Override
    public ApiData editRole(Role role) {
        // todo 表单校验

        // todo 数据合法性校验
        // 角色名不能重复
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name", role.getRoleName());
        wrapper.ne("id", role.getId());
        if(this.count(wrapper)>0) {
            return new ApiData(ApiData.STATUS_FAIL, "角色名不能重复");
        }
        // todo 需改为登录用户
        role.setUpdateUser("admin");
        if(roleMapper.updateById(role) == 1) {
            return new ApiData(ApiData.STATUS_SUCCESS, "修改成功");
        } else {
            return new ApiData(ApiData.STATUS_FAIL, "修改失败");
        }
    }

    @Override
    public ApiData deleteRole(Integer id) {
        UserRole userRole = userRoleMapper.selectById(id);
        if(userRole != null) {
            return ApiData.fail("角色正在使用，不能删除");
        } else if(removeById(id)){
            return ApiData.success("删除成功");
        } else {
            return ApiData.fail("删除失败");
        }
    }
}
