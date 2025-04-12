package com.southwind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.southwind.entity.Property;
import com.southwind.form.PropertyListForm;
import com.southwind.mapper.PropertyMapper;
import com.southwind.service.PropertyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.vo.PageVO;
import com.southwind.vo.PropertyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2023-12-12
 */
@Service
public class PropertyServiceImpl extends ServiceImpl<PropertyMapper, Property> implements PropertyService {

    @Autowired
    private PropertyMapper propertyMapper;

    @Override
    public PageVO propertyList(PropertyListForm propertyListForm) {
        Page<Property> propertyPage = new Page<>(propertyListForm.getPage(), propertyListForm.getLimit());
        QueryWrapper<Property> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(propertyListForm.getPropertyName()), "property_name", propertyListForm.getPropertyName());
        Page<Property> selectPage = this.propertyMapper.selectPage(propertyPage, queryWrapper);
        PageVO pageVO = new PageVO();
        List<PropertyVO> list = new ArrayList<>();
        for (Property record : selectPage.getRecords()) {
            PropertyVO vo = new PropertyVO();
            BeanUtils.copyProperties(record, vo);
            if(record.getStatus() == 1) {
                vo.setStatus("正常");
            } else {
                vo.setStatus("禁用");
            }
            list.add(vo);
        }
        pageVO.setList(list);
        pageVO.setTotalCount(selectPage.getTotal());
        pageVO.setPageSize(selectPage.getSize());
        pageVO.setCurrPage(selectPage.getCurrent());
        pageVO.setTotalPage(selectPage.getPages());
        return pageVO;
    }
}
