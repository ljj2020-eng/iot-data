package com.southwind.form;

import lombok.Data;

@Data
public class LogForm {
    private Integer page;
    private Integer limit;
    private String username;
    private String startDate;
    private String endDate;
    private String operation;
}
