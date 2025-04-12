package com.southwind.mapper;

import com.southwind.entity.Park;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2023-12-12
 */
public interface ParkMapper extends BaseMapper<Park> {

    @Select({
            "select park_name from park where park_id = #{id}"
    })
    public String getNameById(Integer id);

    @Select({
            "select monthly_price from park where park_id = #{id}"
    })
    public Integer getMonthlyPrice(Integer id);

}
