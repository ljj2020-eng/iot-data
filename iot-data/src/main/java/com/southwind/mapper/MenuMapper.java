package com.southwind.mapper;

import com.southwind.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2023-07-25
 */
public interface MenuMapper extends BaseMapper<Menu> {

    @Select({
            "select m.menu_id,m.parent_id,m.name,m.path,m.component," +
                    "m.menu_type,m.status,m.icon,m.sort,m.hidden from " +
                    "user_role ur,role_menu rm,menu m where ur.role_id = rm.role_id" +
                    " and rm.menu_id = m.menu_id " +
                    "and ur.user_id = #{userId} order by m.sort"
    })
    public List<Menu> getMenusByUserId(Integer userId);

}
