package com.oscar.springbootstudy.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResourceDto {
    private Integer resourceId;
    private String href;
    private String name;
    private String permissionCode;
    private Integer parentId;
    private Integer isMenu;
    private Integer isLeaf;
    private List<UserResourceDto> childrenResource;
}
