package com.oscar.springbootstudy.commons;

public interface Constain {

    // 分页每页条数
    static final Integer PAGE_SIZE = 10;

    static final Integer ORDER_STATUS_BOOKED = 1;
    static final Integer ORDER_STATUS_DEPOSIT = 2;
    static final Integer ORDER_STATUS_CANCEL = 3;
    static final Integer ORDER_STATUS_CHECKIN = 4;
    static final Integer ORDER_STATUS_CHECKOUT = 5;
}
