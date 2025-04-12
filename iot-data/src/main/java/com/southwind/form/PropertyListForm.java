package com.southwind.form;

import lombok.Data;

@Data
public class PropertyListForm {
    private Long page;
    private Long limit;
    private String propertyName;
}
