package com.oscar.springbootstudy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.oscar.springbootstudy.base.BasePojo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class User extends BasePojo {
    // 此注解在MB插入数据后自动回填ID
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private Timestamp lastLoginTime;
    private String photo;
}
