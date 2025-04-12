package com.southwind.form;

import lombok.Data;

@Data
public class ParkListForm {
    private Long page;
    private Long limit;
    private Integer propertyId;
    private String parkName;
}
