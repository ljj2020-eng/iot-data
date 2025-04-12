package com.southwind.form;

import lombok.Data;

@Data
public class PayListForm {
    private Long page;
    private Long limit;
    private Integer propertyId;
    private Integer parkId;
    private String number;
}
