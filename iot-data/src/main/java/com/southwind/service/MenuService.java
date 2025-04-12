package com.southwind.service;

import com.southwind.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.vo.MenuRoleVO;
import com.southwind.vo.MenuRouterVO;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author admin
 * @since 2023-07-25
 */
public interface MenuService extends IService<Menu> {
    public List<MenuRouterVO> getMenuRouterByUserId(Integer userId);
    public List<MenuRoleVO> getMenuRoleList();
}
