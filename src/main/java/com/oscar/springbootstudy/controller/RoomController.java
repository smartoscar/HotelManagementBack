package com.oscar.springbootstudy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oscar.springbootstudy.base.FastDfsClient;
import com.oscar.springbootstudy.commons.ApiData;
import com.oscar.springbootstudy.pojo.Room;
import com.oscar.springbootstudy.pojo.RoomImg;
import com.oscar.springbootstudy.pojo.dto.RoomDto;
import com.oscar.springbootstudy.service.RoomImgService;
import com.oscar.springbootstudy.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Title: RoomController</p>
 * <p>Description: </p>
 *
 * @author os
 * @version 1.0.0
 * @date 2022/10/28 10:26
 */
@RestController
@RequestMapping("/Room")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomImgService roomImgService;
    @Autowired
    private FastDfsClient fastDfsClient;

    /**
     * @Description: 查询房间
     * @author: os
     * @Date: 2022/10/28 11:06
     * @Param: roomDto
     * @Return: ApiData
     */
    @GetMapping("/list")
    public ApiData list(RoomDto roomDto) {
        IPage<RoomDto> rooms = roomService.pageRooms(roomDto);
        return ApiData.success("查询成功", rooms);
    }

    /**
     * @Description: 根据房型获取所有客房
     * @author: os
     * @Date: 2022/11/7 14:02
     * @Param: 
     * @Return: 
     */
    @GetMapping("/list/{roomTypeId}")
    public ApiData list(@PathVariable Integer roomTypeId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("room_type_id", roomTypeId);
        wrapper.orderBy(true, true, "code");
        List rooms = roomService.list(wrapper);
        return ApiData.success("查询成功", rooms);
    }
    
    /**
     * @Description: 根据客房编号获取客房信息
     * @author: os
     * @Date: 2022/11/7 14:02
     * @Param: 
     * @Return: 
     */
    @RequestMapping("/one/{roomCode}")
    public ApiData one(@PathVariable Integer roomCode) {
        Room one = roomService.lambdaQuery().eq(Room::getCode, roomCode).one();
        return ApiData.success("查询成功", one);
    }

    /**
     * @Description: 增加房间
     * @author: os
     * @Date: 2022/10/28 11:06
     * @Param: room
     * @Return: ApiData
     */
    @PostMapping("/add")
    public ApiData add(@RequestBody Room room) {
        if(roomService.save(room)){
            return ApiData.success("添加成功");
        } else {
            return ApiData.fail("添加失败");
        }
    }

    /**
     * @Description: 修改房间
     * @author: os
     * @Date: 2022/10/28 11:06
     * @Param: room
     * @Return: ApiData
     */
    @PostMapping("/edit")
    public ApiData edit(@RequestBody Room room) {
        if(roomService.updateById(room)){
            return ApiData.success("修改成功");
        } else {
            return ApiData.fail("修改失败");
        }
    }

    /**
     * @Description: 删除房间
     * @author: os
     * @Date: 2022/10/28 11:06
     * @Param: roomDto
     * @Return: ApiData
     */
    @PostMapping("/delete/{id}")
    public ApiData delete(@PathVariable Integer id) {
        if(roomService.deleteRoom(id)){
            return ApiData.success("删除成功");
        } else {
            return ApiData.fail("删除失败");
        }
    }

    /**
     * @Description: 获取房间图片
     * @author: os
     * @Date: 2022/10/28 11:06
     * @Param: id 房间ID
     * @Return: ApiData
     */
    @GetMapping("/listRoomImg/{id}")
    public ApiData listRoomImg(@PathVariable Integer id) {
        List<RoomImg> list = roomImgService.listRoomImg(id);
        return ApiData.success("查询成功", list);
    }

    /**
     * @Description: 上传房间图片
     * @author: os
     * @Date: 2022/10/28 11:06
     * @Param: roomImg
     * @Return: ApiData
     */
    @PostMapping("/addRoomImg")
    public ApiData addRoomImg(@RequestBody RoomImg roomImg) {
        if (roomImgService.save(roomImg)) {
            return ApiData.success("添加客房图片成功");
        } else {
            return ApiData.fail("添加客房图片失败");
        }
    }

    /**
     * @Description: 删除房间图片
     * @author: os
     * @Date: 2022/10/28 11:06
     * @Param: id 房间图片ID
     * @Return: ApiData
     */
    @GetMapping("/deleteRoomImg/{id}")
    public ApiData deleteRoomImg(@PathVariable Integer id) {
        RoomImg roomImg = roomImgService.getById(id);
        String url = roomImg.getUrl();
        fastDfsClient.deleteFile(url);
        if (roomImgService.removeById(id)) {
            return ApiData.success("删除图片成功");
        } else {
            return ApiData.fail("删除图片失败");
        }
    }

    /**
     * @Description: 删除房间图片
     * @author: os
     * @Date: 2022/10/28 11:06
     * @Param: id 房间图片ID
     * @Return: ApiData
     */
    @GetMapping("/enableRoom/{id}")
    public ApiData enableRoom(@PathVariable Integer id) {
        if (roomService.enableRoom(id)) {
            return ApiData.success("修改成功");
        } else {
            return ApiData.fail("修改成功");
        }
    }
}
