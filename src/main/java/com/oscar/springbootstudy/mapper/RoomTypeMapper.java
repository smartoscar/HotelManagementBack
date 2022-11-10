package com.oscar.springbootstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oscar.springbootstudy.pojo.RoomType;
import org.apache.ibatis.annotations.Param;

public interface RoomTypeMapper extends BaseMapper<RoomType> {
    int updateRoomTypeEnable(@Param("id") Integer id);
}
