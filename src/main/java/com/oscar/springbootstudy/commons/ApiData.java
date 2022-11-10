package com.oscar.springbootstudy.commons;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiData {

    public static final Integer STATUS_SUCCESS = 0;
    public static final Integer STATUS_FAIL = 1;

    private Integer status;
    private Integer code;
    private String msg;
    private Object data;

    // 只有状态及消息
    public ApiData(Integer status, String msg){
        this.status = status;
        this.msg = msg;
    }
    // 状态，消息，数据
    public ApiData(Integer status, String msg, Object data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    // 错误消息带code
    public ApiData(Integer status, String msg, Integer code){
        this.status = status;
        this.msg = msg;
        this.code = code;
    }

    public static ApiData success(String msg) {
        return new ApiData(STATUS_SUCCESS, msg);
    }
    public static ApiData success(Object data) {
        return new ApiData(STATUS_SUCCESS, "", data);
    }
    public static ApiData success(String msg, Object data) {
        return new ApiData(STATUS_SUCCESS, msg, data);
    }
    public static ApiData fail(String msg) {
        return new ApiData(STATUS_FAIL, msg);
    }
}
