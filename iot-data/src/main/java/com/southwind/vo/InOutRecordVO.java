package com.southwind.vo;

import lombok.Data;

import java.util.Date;

@Data
public class InOutRecordVO {
    private Integer inOutRecordId;
    private String parkName;
    private String number;
    private Date inTime;
    private Date outTime;
    private String inPic;
    private String outPic;
    private String payType;
}
