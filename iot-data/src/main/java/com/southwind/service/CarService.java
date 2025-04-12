package com.southwind.service;

import com.southwind.entity.Car;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.form.CarListForm;
import com.southwind.vo.PageVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2023-12-13
 */
public interface CarService extends IService<Car> {
    public PageVO carList(CarListForm carListForm);
}
