package com.southwind.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ManualRecordVO {
    private Integer manualRecordId;
    private String communityName;
    private String visitor;
    private String mobile;
    private String cardNo;
    private String termName;
    private String houseNo;
    private String interviewee;
    private String remark;
    private Date inTime;
    private Date outTime;
    private String userName;
    private Date signTime;
}
