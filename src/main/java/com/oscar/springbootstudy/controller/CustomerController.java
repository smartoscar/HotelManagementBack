package com.oscar.springbootstudy.controller;

import com.oscar.springbootstudy.commons.ApiData;
import com.oscar.springbootstudy.pojo.Customer;
import com.oscar.springbootstudy.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Title: CustomerController</p>
 * <p>Description: </p>
 *
 * @author os
 * @version 1.0.0
 * @date 2022/11/7 12:48
 */
@RestController
@RequestMapping("/Customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * @Description:
     * @author: os
     * @Date: 2022/11/7 12:49
     * @Param:
     * @Return:
     */
    @RequestMapping("/one/{id}")
    public ApiData one(@PathVariable Integer id){
        Customer customer = customerService.getById(id);
        if(customer != null) {
            return ApiData.success("查询成功", customer);
        } else {
            return ApiData.fail("查询失败");
        }
    }
}
