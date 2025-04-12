package com.southwind.configuration;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(value="plateocr")
@Component
@Data
@ApiModel(value = "ApiConfiguration",description = "人脸识别参数描述")
public class ApiConfiguration {

    @ApiModelProperty("人脸识别secretId")
    private String secretId;
    @ApiModelProperty("人脸识别secretKey")
    private String secretKey;
    //	服务器ip
    @ApiModelProperty("人脸识别服务器ip")
    private String serverIp;
    //	服务器区域
    @ApiModelProperty("人脸识别服务器区域")
    private String area;
    //	默认分组
    @ApiModelProperty("人脸识别默认分组")
    private String groupId;
    //	用户id前缀
    @ApiModelProperty("人脸识别用户id前缀")
    private String personIdPre;
    //	随机数
    @ApiModelProperty("人脸识别随机数")
    private String nonceStr;
    //	是否使用
    @ApiModelProperty("人脸识别，是否启用人脸识别功能")
    private boolean used = false;
    //	识别准确率
    @ApiModelProperty("人脸识别比对准确度，如符合80%就识别通过")
    private float passPercent;

}
