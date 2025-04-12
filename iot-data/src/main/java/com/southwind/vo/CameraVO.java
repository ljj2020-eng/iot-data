package com.southwind.vo;
import lombok.Data;

import java.util.Date;

@Data
public class CameraVO {
    private Integer cameraId;
    private Integer communityId;
    private String cameraName;
    private String macId;
    private Integer direction;
    private Integer state;
    private Integer seq;
    private String creater;
    private Date createTime;
    private String remark;
    private String communityName;
}
