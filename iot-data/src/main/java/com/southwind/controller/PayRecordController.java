package com.southwind.controller;


import com.southwind.form.PayListForm;
import com.southwind.service.ParkService;
import com.southwind.service.PayRecordService;
import com.southwind.service.PropertyService;
import com.southwind.util.ExcelUtil;
import com.southwind.util.Result;
import com.southwind.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2023-12-13
 */
@RestController
@RequestMapping("/sys/pay")
public class PayRecordController {

    @Autowired
    private PayRecordService payRecordService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ParkService parkService;
    @Value("${upload.excel}")
    private String excel;

    @GetMapping("/list")
    public Result list(PayListForm payListForm){
        Map<String,Object> map = new HashMap<>();
        map.put("propertyList", this.propertyService.list());
        map.put("parkList",this.parkService.list());
        map.put("pageList",this.payRecordService.payList(payListForm));
        return Result.ok().put("data", map);
    }

    @GetMapping("/exportExcel")
    public Result exportExcel(PayListForm payListForm){
        PageVO pageVO = this.payRecordService.payList(payListForm);
        List list = pageVO.getList();
        String path = excel;
        path = ExcelUtil.ExpPayRecordInfo(list,path);
        return Result.ok().put("data", path);
    }

}

