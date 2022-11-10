package com.oscar.springbootstudy.pojo;

import com.oscar.springbootstudy.base.BasePojo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>Title: RoomType</p>
 * <p>Description: </p>
 *
 * @author os
 * @version 1.0.0
 * @date 2022/10/26 10:42
 */
@Data
public class RoomType extends BasePojo {
    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer bedNum;
    private String description;
    private Integer isEnable;
}
