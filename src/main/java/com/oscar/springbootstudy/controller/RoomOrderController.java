package com.oscar.springbootstudy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oscar.springbootstudy.commons.ApiData;
import com.oscar.springbootstudy.pojo.RoomOrder;
import com.oscar.springbootstudy.pojo.dto.RoomOrderDto;
import com.oscar.springbootstudy.service.RoomOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Title: RoomOrderController</p>
 * <p>Description: </p>
 *
 * @author os
 * @version 1.0.0
 * @date 2022/11/3 15:37
 */
@RestController
@RequestMapping("/RoomOrder")
public class RoomOrderController {
    @Autowired
    private RoomOrderService roomOrderService;

    /**
     * @Description: 查询订单
     * @author: os
     * @Date: 2022/11/3 16:01
     * @Param:
     * @Return:
     */
    @PostMapping("list")
    public ApiData list(@RequestBody RoomOrderDto roomOrderDto) {
        IPage<RoomOrderDto> page = roomOrderService.pageRoomOrders(roomOrderDto);
        return ApiData.success("查询成功", page);
    }

    // 添加订单
    @PostMapping("add")
    public ApiData add(@RequestBody RoomOrderDto roomOrderDto) {
       return roomOrderService.addRoomOrder(roomOrderDto);
    }

    // 修改订单
    @PostMapping("edit")
    public ApiData edit(@RequestBody RoomOrderDto roomOrderDto) {
        return roomOrderService.editRoomOrder(roomOrderDto);
    }

    // 退订
    @PostMapping("process")
    public ApiData process(@RequestBody RoomOrderDto roomOrderDto) {
        return roomOrderService.process(roomOrderDto);
    }

    // 入住

    // 结算
}
