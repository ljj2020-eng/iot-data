package com.southwind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.southwind.entity.Park;
import com.southwind.form.ParkListForm;
import com.southwind.mapper.ParkMapper;
import com.southwind.mapper.PropertyMapper;
import com.southwind.service.ParkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.vo.PageVO;
import com.southwind.vo.ParkVO;
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
public class ParkServiceImpl extends ServiceImpl<ParkMapper, Park> implements ParkService {

    @Autowired
    private ParkMapper parkMapper;
    @Autowired
    private PropertyMapper propertyMapper;

    @Override
    public PageVO parkList(ParkListForm parkListForm) {
        Page<Park> parkPage = new Page<>(parkListForm.getPage(), parkListForm.getLimit());
        QueryWrapper<Park> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(parkListForm.getPropertyId() != null,"property_id", parkListForm.getPropertyId())
                .like(StringUtils.isNotBlank(parkListForm.getParkName()), "park_name", parkListForm.getParkName());
        Page<Park> selectPage = this.parkMapper.selectPage(parkPage, queryWrapper);
        PageVO pageVO = new PageVO();
        List<ParkVO> list = new ArrayList<>();
        for (Park record : selectPage.getRecords()) {
            ParkVO vo = new ParkVO();
            BeanUtils.copyProperties(record, vo);
            vo.setPropertyName(this.propertyMapper.getNameById(record.getPropertyId()));
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
