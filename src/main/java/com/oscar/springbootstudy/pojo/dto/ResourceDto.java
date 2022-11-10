package com.oscar.springbootstudy.pojo.dto;

import com.oscar.springbootstudy.pojo.Resource;
import lombok.Data;

import java.util.List;

/**
 * <p>Title: ResourceDto</p>
 * <p>Description: </p>
 *
 * @author os
 * @version 1.0.0
 * @date 2022/10/24 10:44
 */
@Data
public class ResourceDto {
    private Integer id;
    private Integer parentId;
    private String title;
    private List<ResourceDto> children;
}
