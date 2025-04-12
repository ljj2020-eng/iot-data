package com.southwind.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.LicensePlateOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.LicensePlateOCRResponse;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public class ParkApi {

    public static String getNumber(String fileBase64){
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户 SecretId 和 SecretKey
            Credential cred = new Credential(
                    "AKIDxiEVXM2HNnkUzFsKDwl174oKgnfLD66r",
                    "e7MMGCGIDzYvBDQSFo8D3QYXJq812345");
            // 实例化一个http选项
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("ocr.tencentcloudapi.com");
            // 实例化一个client选项
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象
            OcrClient client = new OcrClient(cred, "", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            LicensePlateOCRRequest req = new LicensePlateOCRRequest();
            // 传入车牌数据
            req.setImageBase64(fileBase64);
            // 返回的resp是一个LicensePlateOCRResponse的实例，与请求对象对应
            LicensePlateOCRResponse resp = client.LicensePlateOCR(req);
            // 获取json格式的字符串回包
            String response = LicensePlateOCRResponse.toJsonString(resp);
            // 解析json数据
            Gson gson = new Gson();
            Map<String,String> map = gson.fromJson(response,
                    new TypeToken<Map<String,String>>(){}.getType());
            // 获取车牌
            String number = map.get("Number");
            return number;
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return null;
    }

}
