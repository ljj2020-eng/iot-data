package com.southwind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.southwind.entity.Car;
import com.southwind.entity.Park;
import com.southwind.form.CarListForm;
import com.southwind.mapper.CarMapper;
import com.southwind.mapper.ParkMapper;
import com.southwind.mapper.PropertyMapper;
import com.southwind.service.CarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.vo.CarVO;
import com.southwind.vo.PageVO;
import com.southwind.vo.ParkVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2023-12-13
 */
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService {

    @Autowired
    private PropertyMapper propertyMapper;
    @Autowired
    private ParkMapper parkMapper;
    @Autowired
    private CarMapper carMapper;

    @Override
    public PageVO carList(CarListForm carListForm) {
        Page<Car> parkPage = new Page<>(carListForm.getPage(), carListForm.getLimit());
        QueryWrapper<Car> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(carListForm.getPropertyId() != null,"property_id", carListForm.getPropertyId())
                .eq(carListForm.getParkId() != null,"park_id",carListForm.getParkId())
                .like(StringUtils.isNotBlank(carListForm.getName()), "name", carListForm.getName())
                .like(StringUtils.isNotBlank(carListForm.getNumber()), "number", carListForm.getNumber())
                .eq(StringUtils.isNotBlank(carListForm.getPhone()), "phone", carListForm.getPhone());
        Page<Car> selectPage = this.carMapper.selectPage(parkPage, queryWrapper);
        PageVO pageVO = new PageVO();
        List<CarVO> list = new ArrayList<>();
        for (Car record : selectPage.getRecords()) {
            CarVO vo = new CarVO();
            BeanUtils.copyProperties(record, vo);
            vo.setPropertyName(this.propertyMapper.getNameById(record.getPropertyId()));
            vo.setParkName(this.parkMapper.getNameById(record.getParkId()));
            Date effectTime = record.getEffectTime();
            if(effectTime == null) {
                vo.setStatus("欠费");
            } else {
                if(effectTime.after(new Date())){
                    vo.setStatus("正常");
                } else {
                    vo.setStatus("欠费");
                }
            }
            list.add(vo);
        }
        pageVO.setList(list);
        pageVO.setTotalPage(selectPage.getPages());
        pageVO.setCurrPage(selectPage.getCurrent());
        pageVO.setPageSize(selectPage.getSize());
        pageVO.setTotalCount(selectPage.getTotal());
        return pageVO;
    }
}
