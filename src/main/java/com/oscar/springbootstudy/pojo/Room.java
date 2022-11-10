package com.oscar.springbootstudy.pojo;

import com.oscar.springbootstudy.base.BasePojo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>Title: Room</p>
 * <p>Description: </p>
 *
 * @author os
 * @version 1.0.0
 * @date 2022/10/28 10:09
 */
@Data
public class Room extends BasePojo {
    private Integer id;
    private Integer code;
    private String name;
    private BigDecimal price;
    private Integer roomTypeId;
    private String description;
    private Integer status;
    private Integer isEnable;
    private Integer isDeleted;
}
