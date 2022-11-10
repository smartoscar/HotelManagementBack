package com.oscar.springbootstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oscar.springbootstudy.pojo.Room;
import com.oscar.springbootstudy.pojo.dto.RoomDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoomMapper extends BaseMapper<Room> {
    int enableRoom(Integer id);

    List<RoomDto> selectListByParam(Map<String, Object> param);

    List<RoomDto> selectRoomPage(Page<RoomDto> page, @Param("roomDto") RoomDto roomDto);
}
