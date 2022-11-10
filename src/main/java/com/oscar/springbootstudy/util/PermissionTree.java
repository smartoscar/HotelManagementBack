package com.oscar.springbootstudy.util;

import com.oscar.springbootstudy.pojo.Resource;
import com.oscar.springbootstudy.pojo.dto.ResourceDto;
import com.oscar.springbootstudy.pojo.dto.UserResourceDto;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PermissionTree {
    // 根节点
    private static final Integer TREE_ROOT_ID = 0;

    /**
     * 标准版
     */
    public static List<UserResourceDto> getTree(List<UserResourceDto> record) {
        List<UserResourceDto> treeList = new LinkedList();
        for (UserResourceDto urd : record) {
            if (TREE_ROOT_ID.equals(urd.getParentId())) {
                UserResourceDto newUrd = new UserResourceDto();
                newUrd.setPermissionCode(urd.getPermissionCode());
                newUrd.setName(urd.getName());
                newUrd.setParentId(urd.getParentId());
                newUrd.setHref(urd.getHref());
                newUrd.setIsMenu(urd.getIsMenu());
                newUrd.setResourceId(urd.getResourceId());
                newUrd.setIsLeaf(urd.getIsLeaf());
                newUrd.setChildrenResource(getChild(urd.getResourceId(), record));
                treeList.add(newUrd);
            }
        }
        return treeList;
    }

    private static List<UserResourceDto> getChild(Integer id, List<UserResourceDto> record) {
        List<UserResourceDto> childrenList = new LinkedList();
        for (UserResourceDto urd : record) {
            if (id.equals(urd.getParentId())) {
                UserResourceDto newUrd = new UserResourceDto();
                newUrd.setPermissionCode(urd.getPermissionCode());
                newUrd.setName(urd.getName());
                newUrd.setParentId(urd.getParentId());
                newUrd.setHref(urd.getHref());
                newUrd.setIsMenu(urd.getIsMenu());
                newUrd.setResourceId(urd.getResourceId());
                newUrd.setIsLeaf(urd.getIsLeaf());
                newUrd.setChildrenResource(getChild(urd.getResourceId(), record));
                childrenList.add(newUrd);
            }
        }
        return childrenList;
    }

    /**
     * lamda表达式版
     */
    public static List<ResourceDto> getTreeEX(List<Resource> record){
        return record.stream().filter(sysDept -> {return TREE_ROOT_ID.equals(sysDept.getParentId());}).map(res -> {
            ResourceDto treeVO = new ResourceDto();
            treeVO.setId(res.getId());
            treeVO.setTitle(res.getTitle());
            treeVO.setChildren(getChildEX(res.getId(), record));
            return treeVO;
        }).collect(Collectors.toList());
    }


    private static List<ResourceDto> getChildEX(Integer id, List<Resource> record){
        return record.stream().filter(res -> {return id.equals(res.getParentId());}).map(res -> {
            ResourceDto treeVO = new ResourceDto();
            treeVO.setId(res.getId());
            treeVO.setTitle(res.getTitle());
            treeVO.setChildren(getChildEX(res.getId(),record));
            return treeVO;
        }).collect(Collectors.toList());
    }
}
