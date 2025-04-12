package com.southwind.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class CarVO {
    private Integer carId;
    private String propertyName;
    private String parkName;
    private String number;
    private String name;
    private String gender;
    private String phone;
    private String status;
    private Date effectTime;
    private String space;
    private String remark;
}
