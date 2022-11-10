package com.oscar.springbootstudy.service;

import com.oscar.springbootstudy.base.BaseService;
import com.oscar.springbootstudy.pojo.RoomImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomImgService extends BaseService<RoomImg> {
    List<RoomImg> listRoomImg(@Param("id") Integer id);
}
