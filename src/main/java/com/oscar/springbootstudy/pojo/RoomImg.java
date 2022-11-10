package com.oscar.springbootstudy.pojo;

import com.oscar.springbootstudy.base.BasePojo;
import lombok.Data;

/**
 * <p>Title: RoomImg</p>
 * <p>Description: </p>
 *
 * @author os
 * @version 1.0.0
 * @date 2022/10/28 11:01
 */
@Data
public class RoomImg extends BasePojo {
    private Integer id;
    private Integer roomId;
    private String url;
}
