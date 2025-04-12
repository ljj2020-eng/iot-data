package com.southwind.vo;

import lombok.Data;

@Data
public class UserEditVO {
    private Integer userId;
    private String username;
    private String password;
    private Integer roleId;
    private String realName;
    private String contact;
    private String mobile;
    private Integer status;
}
