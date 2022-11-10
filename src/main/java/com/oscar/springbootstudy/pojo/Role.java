package com.oscar.springbootstudy.pojo;

import com.oscar.springbootstudy.base.BasePojo;
import lombok.Data;

@Data
public class Role extends BasePojo {
    private Integer id;
    private String roleName;
    private Integer parentId;
}
