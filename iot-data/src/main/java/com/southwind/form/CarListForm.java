package com.southwind.form;

import lombok.Data;

@Data
public class CarListForm {
    private Long page;
    private Long limit;
    private Integer propertyId;
    private Integer parkId;
    private String name;
    private String number;
    private String phone;
}
