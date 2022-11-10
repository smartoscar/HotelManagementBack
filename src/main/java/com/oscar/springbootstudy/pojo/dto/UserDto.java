package com.oscar.springbootstudy.pojo.dto;

import com.oscar.springbootstudy.base.BaseDto;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDto extends BaseDto {

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private Timestamp lastLoginTime;
    private String createUser;
    private Timestamp createTime;
    private String updateUser;
    private Timestamp updateTime;
    private String photo;

    private Integer roleId;
    private String roleName;
}
