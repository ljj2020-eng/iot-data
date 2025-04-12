package com.southwind.vo;

import lombok.Data;

import java.util.List;

@Data
public class MenuRoleVO {
    private Integer id;
    private Integer parentId;
    private List<MenuRoleVO> children;
    private String name;
    private String path;
    private Boolean menuType;
    private Integer status;
    private String icon;
    private Integer sort;
    private String hidden;
}
