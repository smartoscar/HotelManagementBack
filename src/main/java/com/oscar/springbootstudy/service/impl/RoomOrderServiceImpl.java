package com.oscar.springbootstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oscar.springbootstudy.base.BaseServiceImpl;
import com.oscar.springbootstudy.commons.ApiData;
import com.oscar.springbootstudy.commons.Constain;
import com.oscar.springbootstudy.mapper.RoomOrderMapper;
import com.oscar.springbootstudy.pojo.Customer;
import com.oscar.springbootstudy.pojo.RoomOrder;
import com.oscar.springbootstudy.pojo.dto.RoomOrderDto;
import com.oscar.springbootstudy.service.CustomerService;
import com.oscar.springbootstudy.service.RoomOrderService;
import com.oscar.springbootstudy.util.LocalDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
public class RoomOrderServiceImpl extends BaseServiceImpl<RoomOrderMapper, RoomOrder> implements RoomOrderService {

    @Autowired
    private RoomOrderMapper roomOrderMapper;
    @Autowired
    private CustomerService customerService;

    @Override
    public IPage<RoomOrderDto> pageRoomOrders(RoomOrderDto roomOrderDto) {
        Page<RoomOrderDto> page = new Page<>(roomOrderDto.getPageIndex(), roomOrderDto.getPageSize());
        List<RoomOrderDto> result = roomOrderMapper.selectRoomOrderPage(page, roomOrderDto);
        page.setRecords(result);
        return page;
    }

    @Override
    public ApiData addRoomOrder(RoomOrderDto dto) {
        // 添加顾客信息
        Customer customer = new Customer();
        customer.setAccount(dto.getPhone());
        customer.setPhone(dto.getPhone());
        customer.setIdentifyId(dto.getIdentifyId());
        customer.setIdentifyType(dto.getIdentifyType());
        Long count = customerService.lambdaQuery()
                .eq(Customer::getAccount, dto.getPhone())
                .or()
                .eq(Customer::getPhone, dto.getPhone()).count();
        if(count > 0) {
            return ApiData.fail("电话号码已注册");
        } else {
            customerService.save(customer);
        }
        // TODO 添加订单信息
        RoomOrder roomOrder = new RoomOrder();
        roomOrder.setAmount(dto.getAmount());
        roomOrder.setRoomCode(dto.getRoomCode());
        roomOrder.setCustomerId(customer.getId());
        roomOrder.setDeposit(dto.getDeposit());
        // 计算天数
        Integer integer = LocalDateUtils.daysBetweenTwoDates(dto.getStartDate(), dto.getEndDate());
        roomOrder.setDays(integer);
        roomOrder.setStartDate(dto.getStartDate());
        roomOrder.setEndDate(dto.getEndDate());
        roomOrder.setCheckinTime(dto.getCheckinTime());
        roomOrder.setStatus(dto.getStatus());
        roomOrder.setAmount(dto.getAmount());

        // TODO 生成订单ID YYYYMMDDhhmmss+房号
        String orderId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.CHINA)) + dto.getRoomCode();
        roomOrder.setOrderId(orderId);

        if (save(roomOrder)) {
            return ApiData.success("添加成功");
        } else {
            return ApiData.fail("添加失败");
        }
    }

    @Override
    public ApiData editRoomOrder(RoomOrderDto dto) {
        // TODO 修改订单信息
        RoomOrder roomOrder = new RoomOrder();
        roomOrder.setId(dto.getId());
        roomOrder.setAmount(dto.getAmount());
        roomOrder.setRoomCode(dto.getRoomCode());
        roomOrder.setDeposit(dto.getDeposit());
        // 计算天数
        Integer integer = LocalDateUtils.daysBetweenTwoDates(dto.getStartDate(), dto.getEndDate());
        roomOrder.setDays(integer);
        roomOrder.setStartDate(dto.getStartDate());
        roomOrder.setEndDate(dto.getEndDate());
        roomOrder.setCheckinTime(dto.getCheckinTime());
        roomOrder.setStatus(dto.getStatus());
        roomOrder.setAmount(dto.getAmount());
        if (updateById(roomOrder)) {
            return ApiData.success("修改成功");
        } else {
            return ApiData.fail("修改失败");
        }
    }

    @Override
    @Transactional
    public ApiData process(RoomOrderDto roomOrderDto) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        Integer status = roomOrderDto.getStatus();
        String process = "操作";
        RoomOrder roomOrder = new RoomOrder();
        roomOrder.setId(roomOrderDto.getId());
        roomOrder.setStatus(status);
        roomOrder.setUpdateTime(now);
        if(Constain.ORDER_STATUS_CANCEL.equals(status)){
            // TODO 生成退款交易订单
            // 取消
            process = "取消";
        } else if(Constain.ORDER_STATUS_CHECKIN.equals(status)){
            // 入住
            roomOrder.setCheckinTime(now);
            process = "入住";
        } else if(Constain.ORDER_STATUS_CHECKOUT.equals(status)){
            // TODO 生成结算交易订单
            // 结算
            roomOrder.setCheckoutTime(now);
            process = "结算";
        }
        if(roomOrderMapper.updateById(roomOrder) == 1) {
            return ApiData.success(process + "成功");
        } else {
            return ApiData.fail(process + "失败");
        }
    }
}
