package com.oscar.springbootstudy.service;

import com.oscar.springbootstudy.base.BaseService;
import com.oscar.springbootstudy.commons.ApiData;
import com.oscar.springbootstudy.pojo.Resource;

import java.util.List;

public interface ResourceService extends BaseService<Resource> {

    List<String> getUserResourceByUsername(String username);

    ApiData delete(Integer id);
}
