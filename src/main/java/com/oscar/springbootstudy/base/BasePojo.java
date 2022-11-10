package com.oscar.springbootstudy.base;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * <p>Title: BasePojo</p>
 * <p>Description: </p>
 *
 * @author os
 * @version 1.0.0
 * @date 2022/10/26 10:43
 */
@Data
public class BasePojo implements Serializable {
    private String createUser;
    private String updateUser;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
