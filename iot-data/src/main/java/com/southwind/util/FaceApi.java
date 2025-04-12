package com.southwind.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.southwind.configuration.ApiConfiguration;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.iai.v20180301.IaiClient;
import com.tencentcloudapi.iai.v20180301.models.*;
import org.apache.log4j.Logger;

public class FaceApi {

    private Logger logger = Logger.getLogger(FaceApi.class);

    //人脸分析
    public RootResp detectFace(ApiConfiguration config, String url) {
        RootResp result = new RootResp();
        try{
            Credential cred = new Credential(config.getSecretId(), config.getSecretKey());
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(config.getServerIp());
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            IaiClient client = new IaiClient(cred, config.getArea(), clientProfile);
            JSONObject paramObj = new JSONObject();
            paramObj.put("Url", url);
            paramObj.put("MaxFaceNum",1);
            paramObj.put("MinFaceSize",34);
            paramObj.put("NeedFaceAttributes",0);
            paramObj.put("NeedQualityDetection",1);
            DetectFaceRequest req = DetectFaceRequest.fromJsonString(paramObj.toJSONString(),DetectFaceRequest.class);
            DetectFaceResponse resp = client.DetectFace(req);
            result.setData(DetectFaceResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            result.setRet(-1);
            result.setMsg(e.toString());
            logger.error(e.toString());
        }
        logger.info(result);
        return result;
    }

    //添加个体
    public RootResp newperson(ApiConfiguration config, String personId, String personName, String image) {
        RootResp result = new RootResp();
        try{
            Credential cred = new Credential(config.getSecretId(), config.getSecretKey());
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(config.getServerIp());
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            IaiClient client = new IaiClient(cred, config.getArea(), clientProfile);
            JSONObject paramObj = new JSONObject();
            paramObj.put("GroupId", config.getGroupId());
            paramObj.put("PersonId", config.getPersonIdPre() + personId);
            paramObj.put("PersonName", personName);
            paramObj.put("Image", image);
            CreatePersonRequest req = CreatePersonRequest.fromJsonString(paramObj.toJSONString(), CreatePersonRequest.class);
            CreatePersonResponse resp = client.CreatePerson(req);
            result.setData(CreatePersonResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            result.setRet(-1);
            result.setMsg(e.toString());
            logger.error(e.toString());
        }
        logger.info(result);
        return result;
    }

    //删除个体
    public RootResp delperson(ApiConfiguration config, String personId) {
        RootResp result = new RootResp();
        try{
            Credential cred = new Credential(config.getSecretId(), config.getSecretKey());
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(config.getServerIp());
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            IaiClient client = new IaiClient(cred, config.getArea(), clientProfile);
            JSONObject paramObj = new JSONObject();
            paramObj.put("PersonId", config.getPersonIdPre() + personId);
            DeletePersonRequest req = DeletePersonRequest.fromJsonString(paramObj.toJSONString(), DeletePersonRequest.class);
            DeletePersonResponse resp = client.DeletePerson(req);
            result.setData(DeletePersonResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            result.setRet(-1);
            result.setMsg(e.toString());
            logger.error(e.toString());
        }
        logger.info(result);
        return result;
    }

    //增加人脸
    public RootResp addface(ApiConfiguration config, String personId, String image) {
        RootResp result = new RootResp();
        try{
            Credential cred = new Credential(config.getSecretId(), config.getSecretKey());
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(config.getServerIp());
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            IaiClient client = new IaiClient(cred, config.getArea(), clientProfile);
            JSONObject paramObj = new JSONObject();
            JSONArray images = new JSONArray();
            images.add(image);
            paramObj.put("PersonId", config.getPersonIdPre() + personId);
            paramObj.put("Images", images);
            CreateFaceRequest req = CreateFaceRequest.fromJsonString(paramObj.toJSONString(), CreateFaceRequest.class);
            CreateFaceResponse resp = client.CreateFace(req);
            result.setData(CreateFaceResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            result.setRet(-1);
            result.setMsg(e.toString());
            logger.error(e.toString());
        }
        logger.info(result);
        return result;
    }

    //删除人脸
    public RootResp delface(ApiConfiguration config, String personId, String faceId) {
        RootResp result = new RootResp();
        try{
            Credential cred = new Credential(config.getSecretId(), config.getSecretKey());
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(config.getServerIp());
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            IaiClient client = new IaiClient(cred, config.getArea(), clientProfile);
            JSONObject paramObj = new JSONObject();
            JSONArray faces = new JSONArray();
            faces.add(faceId);
            paramObj.put("PersonId", config.getPersonIdPre() + personId);
            paramObj.put("FaceIds", faces);
            DeleteFaceRequest req = DeleteFaceRequest.fromJsonString(paramObj.toJSONString(), DeleteFaceRequest.class);
            DeleteFaceResponse resp = client.DeleteFace(req);
            result.setData(DeleteFaceResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            result.setRet(-1);
            result.setMsg(e.toString());
            logger.error(e.toString());
        }
        logger.info(result);
        return result;
    }

    //人脸验证
    public RootResp faceVerify(ApiConfiguration config, String personId, String image) {
        RootResp result = new RootResp();
        try{
            Credential cred = new Credential(config.getSecretId(), config.getSecretKey());
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(config.getServerIp());
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            IaiClient client = new IaiClient(cred, config.getArea(), clientProfile);
            JSONObject paramObj = new JSONObject();
            paramObj.put("PersonId", config.getPersonIdPre() + personId);
            paramObj.put("Image", image);
            VerifyFaceRequest req = VerifyFaceRequest.fromJsonString(paramObj.toJSONString(), VerifyFaceRequest.class);
            VerifyFaceResponse resp = client.VerifyFace(req);
            result.setData(VerifyFaceResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            result.setRet(-1);
            result.setMsg(e.toString());
            logger.error(e.toString());
        }
        logger.info(result);
        return result;
    }

    //人员搜索按库返回
    public RootResp searchPersonsReturnsByGroup(ApiConfiguration config, String image) {
        RootResp result = new RootResp();
        try{
            Credential cred = new Credential(config.getSecretId(), config.getSecretKey());
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(config.getServerIp());
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            IaiClient client = new IaiClient(cred, config.getArea(), clientProfile);
            JSONObject paramObj = new JSONObject();
            paramObj.put("GroupIds", new String[] {config.getGroupId()});
            paramObj.put("Image", image);
            //最多返回的最相似人员数目
            paramObj.put("MaxPersonNumPerGroup", 5);
            //返回人员具体信息
            paramObj.put("NeedPersonInfo", 1);
            //最多识别的人脸数目
            paramObj.put("MaxFaceNum", 1);
            SearchFacesReturnsByGroupRequest req = SearchFacesReturnsByGroupRequest.fromJsonString(paramObj.toJSONString(), SearchFacesReturnsByGroupRequest.class);
            SearchFacesReturnsByGroupResponse resp = client.SearchFacesReturnsByGroup(req);
            result.setData(VerifyFaceResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            result.setRet(-1);
            result.setMsg(e.toString());
            logger.error(e.toString());
        }
        logger.info(result);
        return result;
    }
}
