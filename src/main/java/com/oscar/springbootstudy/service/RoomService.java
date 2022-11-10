package com.oscar.springbootstudy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oscar.springbootstudy.base.BaseService;
import com.oscar.springbootstudy.pojo.Room;
import com.oscar.springbootstudy.pojo.dto.RoomDto;

import java.util.List;

public interface RoomService extends BaseService<Room> {
    List<RoomDto> list(String name, Integer roomTypeId, Integer status);

    boolean deleteRoom(Integer id);

    boolean enableRoom(Integer id);

    IPage<RoomDto> pageRooms(RoomDto roomDto);
}
