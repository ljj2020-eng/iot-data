package com.southwind.form;

import lombok.Data;

import java.util.List;

@Data
public class RoleAddOrUpdateForm {
    private Integer roleId;
    private String roleName;
    private Integer type;
    private String remark;
    private List<Integer> menuIdList;
}
