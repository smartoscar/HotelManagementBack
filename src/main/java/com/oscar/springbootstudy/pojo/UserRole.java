package com.oscar.springbootstudy.pojo;

import com.oscar.springbootstudy.base.BasePojo;
import lombok.Data;

@Data
public class UserRole extends BasePojo {
    private Long id;
    private Long userId;
    private Integer roleId;
}
