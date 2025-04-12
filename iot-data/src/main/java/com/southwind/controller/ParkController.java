package com.southwind.controller;


import com.southwind.annotation.LogAnnotation;
import com.southwind.entity.Park;
import com.southwind.form.ParkListForm;
import com.southwind.service.ParkService;
import com.southwind.service.PropertyService;
import com.southwind.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2023-12-12
 */
@RestController
@RequestMapping("/sys/park")
public class ParkController {

    @Autowired
    private ParkService parkService;
    @Autowired
    private PropertyService propertyService;

    @GetMapping("/list")
    public Result list(ParkListForm parkListForm){
        Map<String,Object> map = new HashMap<>();
        map.put("propertyList", this.propertyService.list());
        map.put("pageList",this.parkService.parkList(parkListForm));
        return Result.ok().put("data", map);
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Integer id){
        if(id == 0) {
            Map<String, List> map = new HashMap<>();
            map.put("propertyList", this.propertyService.list());
            return Result.ok().put("data",map);
        } else {
            Map<String,Object> map = new HashMap<>();
            map.put("park", this.parkService.getById(id));
            map.put("propertyList", this.propertyService.list());
            return Result.ok().put("data", map);
        }
    }

    @LogAnnotation("添加停车场")
    @PostMapping("/add")
    public Result add(@RequestBody Park park){
        boolean save = this.parkService.save(park);
        if(!save) return Result.error("停车场添加失败");
        return Result.ok();
    }

    @LogAnnotation("编辑停车场")
    @PutMapping("/edit")
    public Result edit(@RequestBody Park park){
        boolean updateById = this.parkService.updateById(park);
        if(!updateById) return Result.error("停车场修改失败");
        return Result.ok();
    }

    @LogAnnotation("删除停车场")
    @DeleteMapping("/del")
    public Result del(@RequestBody Integer[] ids){
        boolean removeByIds = this.parkService.removeByIds(Arrays.asList(ids));
        if(!removeByIds) return Result.error("停车场删除失败");
        return Result.ok();
    }

    @GetMapping("/getParkMap")
    public Result getParkMap(){
        List<Park> list = this.parkService.list();
        if(list == null) return Result.error("没有停车场数据");
        return Result.ok().put("data", list);
    }

}

