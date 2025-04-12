package com.southwind.controller;


import com.southwind.form.LogForm;
import com.southwind.service.LogService;
import com.southwind.util.Result;
import com.southwind.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Arrays;

/**
 * <p>
 * 操作日志表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2023-08-04
 */
@RestController
@RequestMapping("/sys/log")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/list")
    public Result list(LogForm logForm){
        PageVO pageVO = this.logService.logList(logForm);
        return Result.ok().put("data", pageVO);
    }

    @DeleteMapping("/del")
    public Result del(@RequestBody Integer[] ids){
        boolean removeByIds = this.logService.removeByIds(Arrays.asList(ids));
        if(!removeByIds) return Result.error("删除日志失败");
        return Result.ok();
    }
}

