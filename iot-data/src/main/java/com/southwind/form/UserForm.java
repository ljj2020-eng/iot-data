package com.southwind.form;

import lombok.Data;

@Data
public class UserForm {
    private Integer page;
    private Integer limit;
    private String username;
    private String realName;
}
