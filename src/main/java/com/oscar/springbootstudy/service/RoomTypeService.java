package com.oscar.springbootstudy.service;

import com.oscar.springbootstudy.base.BaseService;
import com.oscar.springbootstudy.pojo.RoomType;

public interface RoomTypeService extends BaseService<RoomType> {
    boolean enable(Integer id);
}
