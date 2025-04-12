package com.southwind;

import com.southwind.entity.Menu;
import com.southwind.mapper.MenuMapper;
import com.southwind.mapper.RoleMapper;
import com.southwind.service.MenuService;
import com.southwind.vo.MenuRouterVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CommunityDemoApplicationTests {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private MenuService menuService;

    @Test
    void contextLoads() {
        List<MenuRouterVO> menuRouterByUserId = menuService.getMenuRouterByUserId(1);
        for (MenuRouterVO menuRouterVO : menuRouterByUserId) {

        }
    }

}
