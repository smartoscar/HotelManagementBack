package com.oscar.springbootstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oscar.springbootstudy.pojo.RoomOrder;
import com.oscar.springbootstudy.pojo.dto.RoomOrderDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomOrderMapper extends BaseMapper<RoomOrder> {
    List<RoomOrderDto> selectRoomOrderPage(Page<RoomOrderDto> page, @Param("dto") RoomOrderDto roomOrderDto);

    boolean updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
