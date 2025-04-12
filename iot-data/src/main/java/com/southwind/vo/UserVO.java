package com.southwind.vo;

import lombok.Data;

@Data
public class UserVO {
    private Integer userId;
    private String username;
    private String roleName;
    private String realName;
    private String contact;
    private String mobile;
    private Integer status;
}
