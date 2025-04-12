package com.southwind.mapper;

import com.southwind.entity.Property;
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
public interface PropertyMapper extends BaseMapper<Property> {

    @Select({
            "select property_name from property where property_id = #{id}"
    })
    public String getNameById(Integer id);

}
