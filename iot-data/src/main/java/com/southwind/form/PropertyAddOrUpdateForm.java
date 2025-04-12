package com.southwind.form;

import lombok.Data;

@Data
public class PropertyAddOrUpdateForm {
    private Integer propertyId;
    private String propertyCode;
    private String propertyName;
    private String contact;
    private String phone;
    private Integer status;
}
