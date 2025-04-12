package com.southwind.mapper;

import com.southwind.entity.Car;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2023-12-13
 */
public interface CarMapper extends BaseMapper<Car> {

    @Select({
            "select * from car where number = #{number} "
    })
    public Car getByNumber(String number);

}
