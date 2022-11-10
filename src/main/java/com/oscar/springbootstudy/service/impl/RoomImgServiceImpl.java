package com.oscar.springbootstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oscar.springbootstudy.base.BaseServiceImpl;
import com.oscar.springbootstudy.base.FastDfsClient;
import com.oscar.springbootstudy.mapper.RoomImgMapper;
import com.oscar.springbootstudy.mapper.RoomMapper;
import com.oscar.springbootstudy.pojo.Room;
import com.oscar.springbootstudy.pojo.RoomImg;
import com.oscar.springbootstudy.service.RoomImgService;
import com.oscar.springbootstudy.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Watchable;
import java.util.List;

@Service
public class RoomImgServiceImpl extends BaseServiceImpl<RoomImgMapper, RoomImg> implements RoomImgService {
    @Override
    public List<RoomImg> listRoomImg(Integer id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("room_id", id);
        return list(wrapper);
    }
}
