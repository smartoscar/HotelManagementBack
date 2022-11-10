package com.oscar.springbootstudy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oscar.springbootstudy.commons.ApiData;
import com.oscar.springbootstudy.pojo.Resource;
import com.oscar.springbootstudy.service.ResourceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/list/{id}")
    @RequiresPermissions("resource:list")
    public ApiData list(@PathVariable Integer id){
        ApiData apiData;
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("parent_id", id);
        wrapper.orderBy(true, true, "id");
        List<Resource> list = resourceService.list(wrapper);
        if(CollectionUtils.isEmpty(list)) {
            apiData = new ApiData(ApiData.STATUS_FAIL, "数据不存在！");
        } else {
            apiData = new ApiData(ApiData.STATUS_SUCCESS, "查询成功！", list);
        }
        return apiData;
    }

    /**
     * @Description:
     * @author: os
     * @Date: 2022/11/2 12:01
     * @Param:
     * @Return:
     */
    @PostMapping("/add")
    public ApiData add(@RequestBody Resource resource){
        if(resourceService.save(resource)) {
            return ApiData.success("添加成功");
        } else {
            return ApiData.fail("添加失败");
        }
    }

    /**
     * @Description:
     * @author: os
     * @Date: 2022/11/2 12:01
     * @Param:
     * @Return:
     */
    @PostMapping("/edit")
    public ApiData edit(@RequestBody Resource resource){
        if(resourceService.updateById(resource)) {
            return ApiData.success("修改成功");
        } else {
            return ApiData.fail("修改失败");
        }
    }

    @PostMapping("/delete/{id}")
    @RequiresPermissions("resource:delete")
    public ApiData delete(@PathVariable Integer id){
        return resourceService.delete(id);
    }

}
