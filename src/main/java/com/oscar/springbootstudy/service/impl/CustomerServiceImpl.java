package com.oscar.springbootstudy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oscar.springbootstudy.base.BaseServiceImpl;
import com.oscar.springbootstudy.commons.ApiData;
import com.oscar.springbootstudy.mapper.CustomerMapper;
import com.oscar.springbootstudy.mapper.RoomOrderMapper;
import com.oscar.springbootstudy.pojo.Customer;
import com.oscar.springbootstudy.pojo.RoomOrder;
import com.oscar.springbootstudy.pojo.dto.RoomOrderDto;
import com.oscar.springbootstudy.service.CustomerService;
import com.oscar.springbootstudy.service.RoomOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl extends BaseServiceImpl<CustomerMapper, Customer> implements CustomerService {
}
