package com.oscar.springbootstudy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.oscar.springbootstudy.base.BasePojo;
import lombok.Data;

import java.sql.Timestamp;

/**
 * <p>Title: Customer</p>
 * <p>Description: </p>
 *
 * @author os
 * @version 1.0.0
 * @date 2022/11/3 15:26
 */
@Data
public class Customer extends BasePojo {

    // 此注解在MB插入数据后自动回填ID
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String account;
    private String password;
    private String identifyId;
    private Integer identifyType;
    private String phone;
    private Timestamp lastLoginTime;
    private Integer isDeleted;
    private Integer vipLevel;

}
