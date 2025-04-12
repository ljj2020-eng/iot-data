package com.southwind.vo;

import lombok.Data;

import java.util.List;

@Data
public class MenuRouterVO {
    private String name;
    private String path;
    private String component;
    private String hidden;
    private String redirect = "noRedirect";
    private Boolean alwaysShow = true;
    private MetaVO meta;
    private List<ChildMenuRouterVO> children;
}
