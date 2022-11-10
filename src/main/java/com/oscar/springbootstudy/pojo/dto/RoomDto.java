package com.oscar.springbootstudy.pojo.dto;

import com.oscar.springbootstudy.base.BaseDto;
import com.oscar.springbootstudy.base.BasePojo;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * <p>Title: RoomDto</p>
 * <p>Description: </p>
 *
 * @author os
 * @version 1.0.0
 * @date 2022/10/28 10:27
 */
@Data
public class RoomDto extends BaseDto {
    private Integer id;
    private Integer code;
    private String name;
    private BigDecimal price;
    private Integer roomTypeId;
    private String roomTypeName;
    private String description;
    private Integer status;
    private Integer isEnable;
    private Integer isDeleted;
    private String createUser;
    private String updateUser;
    private Timestamp createTime;
    private Timestamp updateTime;
}