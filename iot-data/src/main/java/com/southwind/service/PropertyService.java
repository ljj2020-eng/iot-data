package com.southwind.service;

import com.southwind.entity.Property;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.form.PropertyListForm;
import com.southwind.vo.PageVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2023-12-12
 */
public interface PropertyService extends IService<Property> {
    public PageVO propertyList(PropertyListForm propertyListForm);
}
