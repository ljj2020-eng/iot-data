package com.southwind.vo;

import lombok.Data;

@Data
public class ParkVO {
    private Integer parkId;
    private String propertyName;
    private String parkName;
    private Integer parkCount;
    private Integer monthlyPrice;
    private Integer freeDuration;
    private Integer chargeUnit;
    private Integer chargePrice;
    private Integer maxCharge;
    private Float lng;
    private Float lat;
}
