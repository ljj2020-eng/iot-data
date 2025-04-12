package com.southwind.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.southwind.annotation.LogAnnotation;
import com.southwind.entity.Menu;
import com.southwind.entity.RoleMenu;
import com.southwind.service.MenuService;
import com.southwind.service.RoleMenuService;
import com.southwind.util.Result;
import com.southwind.vo.MenuRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author admin
 * @since 2023-07-25
 */
@RestController
@RequestMapping("/sys/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleMenuService roleMenuService;

    @GetMapping("/list")
    public Result list(){
        List<MenuRoleVO> menuRoleList = this.menuService.getMenuRoleList();
        return Result.ok().put("data", menuRoleList);
    }

    @LogAnnotation("添加菜单")
    @PostMapping("/add")
    public Result add(@RequestBody Menu menu){
        boolean save = this.menuService.save(menu);
        if(!save) return Result.error("添加菜单失败");
        return Result.ok();
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Integer id){
        Menu menu = this.menuService.getById(id);
        return Result.ok().put("data", menu);
    }

    @LogAnnotation("编辑菜单")
    @PutMapping("/edit")
    public Result edit(@RequestBody Menu menu){
        boolean updateById = this.menuService.updateById(menu);
        if(!updateById) return Result.error("编辑菜单失败");
        return Result.ok();
    }

    @LogAnnotation("删除菜单")
    @DeleteMapping("/del/{id}")
    public Result del(@PathVariable("id") Integer id){
        boolean remove = this.menuService.removeById(id);
        QueryWrapper<Menu> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("parent_id", id);
        this.menuService.remove(queryWrapper1);
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_id", id);
        this.roleMenuService.remove(queryWrapper);
        if(remove) return Result.ok();
        return Result.error("删除菜单失败");
    }
}

