package com.southwind.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChildMenuRouterVO {
    private String name;
    private String path;
    private String component;
    private String hidden;
    private MetaVO meta;
}
