package com.southwind.controller;

import com.southwind.annotation.LogAnnotation;
import com.southwind.configuration.ApiConfiguration;
import com.southwind.entity.*;
import com.southwind.form.InOutParkForm;
import com.southwind.form.InOutQueryForm;
import com.southwind.mapper.CarMapper;
import com.southwind.mapper.InOutRecordMapper;
import com.southwind.service.*;
import com.southwind.util.Base64Util;
import com.southwind.util.ParkApi;
import com.southwind.util.ParkUtil;
import com.southwind.util.Result;
import com.southwind.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2023-07-26
 */
@RestController
@RequestMapping("/sys/inOut")
public class InOutRecordController {

    @Autowired
    private InOutRecordService inOutRecordService;
    @Value("${upload.numberUrl}")
    private String numberUrl;
    @Value("${upload.urlPrefix}")
    private String urlPrefix;
    @Autowired
    private InOutRecordMapper inOutRecordMapper;
    @Autowired
    private ParkService parkService;
    @Autowired
    private PayRecordService payRecordService;
    @Autowired
    private CarMapper carMapper;

    @GetMapping("/chart")
    public Result chart(){
        Map map = this.inOutRecordService.chart();
        return Result.ok().put("data", map);
    }

    @GetMapping("/parkList")
    public Result parkList(){
        List<Park> list = this.parkService.list();
        if(list == null) return Result.error("没有停车场数据");
        return Result.ok().put("data", list);
    }

    @LogAnnotation("车牌识别")
    @PostMapping("/add")
    public Result add(@RequestBody InOutParkForm inOutParkForm){
        String fileBase64 = inOutParkForm.getFileBase64();
        //调用腾讯AI接口
//        String number = ParkApi.getNumber(fileBase64);
        //模拟车牌识别
        String faceBase = fileBase64.substring(0, 60);
        String number = ParkUtil.getNumber(faceBase);
        if(number == null){
            return Result.ok().put("status", "fail").put("data", "车牌识别失败");
        }
        //入场出场
        InOutRecord inOutRecord = new InOutRecord();
        inOutRecord.setParkId(inOutParkForm.getParkId());
        inOutRecord.setNumber(number);
        try {
            //保存图片
            String newFileName = UUID.randomUUID()+"." + inOutParkForm.getExtName();
            String fileName = numberUrl + newFileName;
            Base64Util.decoderBase64File(fileBase64,fileName);
            String basePath = urlPrefix + "park/upload/number/" + newFileName;
            //查找系统中是否有该车辆的出入场信息
            InOutRecord inOutRecord1 = this.inOutRecordMapper.getInOutRecord(inOutRecord);
            Park park = this.parkService.getById(inOutRecord.getParkId());
            Car car = this.carMapper.getByNumber(number);
            //进入停车场
            if(inOutRecord1 == null) {
                inOutRecord.setInPic(basePath);
                if(car == null) {
                    inOutRecord.setPayType(1);
                } else {
                    inOutRecord.setPayType(2);
                }
                this.inOutRecordMapper.insert(inOutRecord);
                return Result.ok().put("status", "success").put("data", "【"+ number + "】进入"+"【"+ park.getParkName() +"】");
            } else {
                //离开停车场
                inOutRecord1.setOutPic(basePath);
                this.inOutRecordMapper.updateById(inOutRecord1);
                //存储缴费记录
                PayRecord payRecord = new PayRecord();
                payRecord.setPropertyId(park.getPropertyId());
                payRecord.setParkId(park.getParkId());
                payRecord.setNumber(number);
                String carType = "";
                Integer hour = 0;
                Integer amount = 0;
                Park park1 = this.parkService.getById(inOutRecord1.getParkId());
                //计算停车费
                Map<String, Integer> map = ParkUtil.parkPay(inOutRecord1.getInTime(), inOutRecord1.getOutTime(), park1.getFreeDuration(), park1.getChargePrice(), park1.getMaxCharge());
                if(map != null){
                    hour = map.get("hour");
                    amount = map.get("amount");
                }
                String result = "";
                if(car == null){
                    //临时车
                    payRecord.setPayType(1);
                    carType = "临时车";
                    payRecord.setAmount(amount);
                    result = "【临时车】"+ number +"离开"+"【"+ park.getParkName() +"】停车"+hour+"小时，缴费"+amount+"元";
                } else {
                    //包月车
                    payRecord.setPayType(2);
                    carType = "包月车";
                    Date effectTime = car.getEffectTime();
                    //判断是否过期
                    if(effectTime != null && effectTime.after(new Date())){
                        payRecord.setAmount(0);
                        result = "【包月车】"+ number +"离开"+"【"+ park.getParkName() +"】停车"+hour+"小时，无需缴费";
                    } else {
                        payRecord.setAmount(amount);
                        result = "【包月车】"+ number +"离开"+"【"+ park.getParkName() +"】停车"+hour+"小时，缴费"+amount+"元";
                    }
                }
                this.payRecordService.save(payRecord);
                return Result.ok().put("status", "success").put("data", result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/list")
    public Result list(InOutQueryForm inOutQueryForm){
        PageVO pageVO = this.inOutRecordService.inOutRecordList(inOutQueryForm);
        Map map = new HashMap();
        map.put("pageList",pageVO);
        return Result.ok().put("data", map);
    }
}

