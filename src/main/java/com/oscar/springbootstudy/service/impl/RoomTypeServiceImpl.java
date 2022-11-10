package com.oscar.springbootstudy.service.impl;

import com.oscar.springbootstudy.base.BaseServiceImpl;
import com.oscar.springbootstudy.mapper.RoomTypeMapper;
import com.oscar.springbootstudy.pojo.RoomType;
import com.oscar.springbootstudy.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomTypeServiceImpl extends BaseServiceImpl<RoomTypeMapper, RoomType> implements RoomTypeService {

    @Autowired
    private RoomTypeMapper roomTypeMapper;

    @Override
    public boolean enable(Integer id) {
        return roomTypeMapper.updateRoomTypeEnable(id) == 1;
    }
}
