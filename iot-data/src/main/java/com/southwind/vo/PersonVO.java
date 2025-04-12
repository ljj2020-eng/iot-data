package com.southwind.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class PersonVO {
    private Integer personId;
    private Integer communityId;
    private String termName;
    private String houseNo;
    private String userName;
    private String sex;
    private String mobile;
    private String faceUrl;
    private String personType;
    private Integer state;
    private String creater;
    private Date createTime;
    private String remark;
    private String communityName;
}
