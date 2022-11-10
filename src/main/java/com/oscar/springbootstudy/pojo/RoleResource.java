package com.oscar.springbootstudy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class RoleResource {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer roleId;
    private Integer resourceId;
    private String createUser;
    private Timestamp createTime;
}
