package com.oscar.springbootstudy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oscar.springbootstudy.base.BaseService;
import com.oscar.springbootstudy.commons.ApiData;
import com.oscar.springbootstudy.pojo.RoomOrder;
import com.oscar.springbootstudy.pojo.dto.RoomOrderDto;

public interface RoomOrderService extends BaseService<RoomOrder> {
    IPage<RoomOrderDto> pageRoomOrders(RoomOrderDto roomOrderDto);

    ApiData addRoomOrder(RoomOrderDto dto);

    ApiData editRoomOrder(RoomOrderDto roomOrderDto);

    ApiData process(RoomOrderDto roomOrderDto);
}
