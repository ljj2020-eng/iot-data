package com.southwind.controller;


import com.southwind.annotation.LogAnnotation;
import com.southwind.entity.*;
import com.southwind.form.PropertyAddOrUpdateForm;
import com.southwind.form.PropertyListForm;
import com.southwind.service.PropertyService;
import com.southwind.util.Result;
import com.southwind.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2023-12-12
 */
@RestController
@RequestMapping("/sys/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @GetMapping("/list")
    public Result list(PropertyListForm propertyListForm){
        PageVO pageVO = this.propertyService.propertyList(propertyListForm);
        return Result.ok().put("data", pageVO);
    }

    @LogAnnotation("添加物业公司")
    @PostMapping("/add")
    public Result add(@RequestBody PropertyAddOrUpdateForm propertyAddOrUpdateForm){
        Property property = new Property();
        BeanUtils.copyProperties(propertyAddOrUpdateForm, property);
        property.setStatus(1);
        boolean save = this.propertyService.save(property);
        if(!save) return Result.error("小区添加失败");
        return Result.ok();
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Integer id){
        Property property = this.propertyService.getById(id);
        if(property == null) return Result.error("物业公司不存在");
        return Result.ok().put("data", property);
    }

    @LogAnnotation("编辑物业公司")
    @PutMapping("/edit")
    public Result edit(@RequestBody PropertyAddOrUpdateForm propertyAddOrUpdateForm){
        Property property = new Property();
        BeanUtils.copyProperties(propertyAddOrUpdateForm, property);
        boolean updateById = this.propertyService.updateById(property);
        if(!updateById) return Result.error("编辑物业公司失败");
        return Result.ok();
    }

    @LogAnnotation("删除物业公司")
    @DeleteMapping("/del")
    public Result del(@RequestBody Integer[] ids){
        boolean remove = this.propertyService.removeByIds(Arrays.asList(ids));
        if(!remove) return Result.error("物业公司删除失败");
        return Result.ok();
    }
}

