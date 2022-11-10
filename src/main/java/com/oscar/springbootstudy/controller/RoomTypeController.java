package com.oscar.springbootstudy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oscar.springbootstudy.commons.ApiData;
import com.oscar.springbootstudy.pojo.RoomType;
import com.oscar.springbootstudy.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Title: RoomTypeController</p>
 * <p>Description: </p>
 *
 * @author os
 * @version 1.0.0
 * @date 2022/10/26 10:52
 */
@RestController
@RequestMapping("/RoomType")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    /**
     * @Description: 房型列表查询
     * @author: os
     * @Date: 2022/10/26 10:54
     * @Param:
     * @Return: ApiData
     */
    @GetMapping("/list")
    public ApiData list() {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.orderBy(true, true, "id");
        List<RoomType> list = roomTypeService.list(wrapper);
        return ApiData.success(list);
    }

    /**
     * @Description: 房型列表查询
     * @author: os
     * @Date: 2022/10/26 10:54
     * @Param:
     * @Return: ApiData
     */
    @GetMapping("/listEnable")
    public ApiData listEnable() {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("is_enable", 1);
        wrapper.orderBy(true, true, "id");
        List<RoomType> list = roomTypeService.list(wrapper);
        return ApiData.success(list);
    }

    /**
     * @Description: 添加房型
     * @author: os
     * @Date: 2022/10/26 10:54
     * @Param: roomType
     * @Return: ApiData
     */
    @PostMapping("/add")
    public ApiData add(@RequestBody RoomType roomType) {
        if(roomTypeService.save(roomType)) {
            return ApiData.success("添加成功");
        } else {
            return ApiData.fail("添加失败");
        }
    }

    /**
     * @Description: 修改房型
     * @author: os
     * @Date: 2022/10/26 10:54
     * @Param: roomType
     * @Return: ApiData
     */
    @PostMapping("/edit")
    public ApiData edit(@RequestBody RoomType roomType) {
        if(roomTypeService.updateById(roomType)) {
            return ApiData.success("修改成功");
        } else {
            return ApiData.fail("修改失败");
        }
    }

    /**
     * @Description: 删除房型
     * @author: os
     * @Date: 2022/10/26 10:54
     * @Param: id 房型ID
     * @Return: ApiData
     */
    @PostMapping("/delete/{id}")
    public ApiData delete(@PathVariable Integer id) {
        // TODO 删除之前需判断是否有被引用
        if(roomTypeService.removeById(id)) {
            return ApiData.success("删除成功");
        } else {
            return ApiData.fail("删除失败");
        }
    }

    /**
     * @Description: 开启关闭房型
     * @author: os
     * @Date: 2022/10/26 10:54
     * @Param: 房型ID
     * @Return: ApiData
     */
    @PostMapping("/enable/{id}")
    public ApiData enable(@PathVariable Integer id) {
        // TODO 删除之前需判断是否有被引用
        if(roomTypeService.enable(id)) {
            return ApiData.success("修改成功");
        } else {
            return ApiData.fail("修改失败");
        }
    }

}
