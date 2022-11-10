package com.oscar.springbootstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oscar.springbootstudy.base.BaseServiceImpl;
import com.oscar.springbootstudy.base.FastDfsClient;
import com.oscar.springbootstudy.mapper.RoomMapper;
import com.oscar.springbootstudy.pojo.Room;
import com.oscar.springbootstudy.pojo.RoomImg;
import com.oscar.springbootstudy.pojo.dto.RoomDto;
import com.oscar.springbootstudy.pojo.dto.UserDto;
import com.oscar.springbootstudy.service.RoomImgService;
import com.oscar.springbootstudy.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomServiceImpl extends BaseServiceImpl<RoomMapper, Room> implements RoomService {

    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private RoomImgService roomImgService;
    @Autowired
    private FastDfsClient fastDfsClient;

    @Override
    public List<RoomDto> list(String name, Integer roomTypeId, Integer status) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        param.put("roomTypeId", roomTypeId);
        param.put("status", status);
        return roomMapper.selectListByParam(param);
    }

    @Override
    public IPage<RoomDto> pageRooms(RoomDto roomDto) {

        Page<RoomDto> page = new Page<>(roomDto.getPageIndex(), roomDto.getPageSize());
        List<RoomDto> result = roomMapper.selectRoomPage(page, roomDto);
        page.setRecords(result);
        return page;
    }

    @Override
    @Transactional
    public boolean deleteRoom(Integer id) {
        // TODO 判断是否被预定或入住

        // 删除此客房的所有图片
        List<RoomImg> list = roomImgService.listRoomImg(id);
        list.forEach(e -> {
            fastDfsClient.deleteFile(e.getUrl());
        });
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("room_id", id);
        roomImgService.remove(wrapper);

        // TODO 逻辑删除房间，涉及历史订单及入住记录
        return removeById(id);
    }

    @Override
    public boolean enableRoom(Integer id) {
        return roomMapper.enableRoom(id) == 1;
    }

}
