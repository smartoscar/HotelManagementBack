package com.oscar.springbootstudy.pojo;

import com.oscar.springbootstudy.base.BasePojo;
import lombok.Data;

@Data
public class Resource extends BasePojo {
    private Integer id;
    private String title;
    private Integer parentId;
    private String href;
    private Integer isEnable;
    private Integer isMenu;
    private Integer isLeaf;
    private String permissionCode;
    private String permissionName;
}
