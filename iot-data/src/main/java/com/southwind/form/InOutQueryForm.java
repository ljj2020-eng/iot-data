package com.southwind.form;

import lombok.Data;

@Data
public class InOutQueryForm {
    private Integer page;
    private Integer limit;
    private String number;
    private String startDate;
    private String endDate;
}
