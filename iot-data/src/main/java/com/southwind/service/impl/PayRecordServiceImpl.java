package com.southwind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.southwind.entity.PayRecord;
import com.southwind.form.PayListForm;
import com.southwind.mapper.ParkMapper;
import com.southwind.mapper.PayRecordMapper;
import com.southwind.mapper.PropertyMapper;
import com.southwind.service.PayRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.vo.PageVO;
import com.southwind.vo.PayRecordVO;
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
 * @since 2023-12-13
 */
@Service
public class PayRecordServiceImpl extends ServiceImpl<PayRecordMapper, PayRecord> implements PayRecordService {

    @Autowired
    private PayRecordMapper payRecordMapper;
    @Autowired
    private PropertyMapper propertyMapper;
    @Autowired
    private ParkMapper parkMapper;

    @Override
    public PageVO payList(PayListForm payListForm) {
        //分页
        //条件检索
        Page<PayRecord> payRecordPage = new Page<>(payListForm.getPage(), payListForm.getLimit());
        QueryWrapper<PayRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(payListForm.getPropertyId() != null, "property_id",payListForm.getPropertyId())
                .eq(payListForm.getParkId() != null, "park_id", payListForm.getParkId())
                .like(StringUtils.isNotBlank(payListForm.getNumber()), "number", payListForm.getNumber());
        Page<PayRecord> selectPage = this.payRecordMapper.selectPage(payRecordPage, queryWrapper);
        PageVO pageVO = new PageVO();
        List<PayRecordVO> list = new ArrayList<>();
        for (PayRecord record : selectPage.getRecords()) {
            PayRecordVO vo = new PayRecordVO();
            BeanUtils.copyProperties(record, vo);
            vo.setPropertyName(this.propertyMapper.getNameById(record.getPropertyId()));
            vo.setParkName(this.parkMapper.getNameById(record.getParkId()));
            switch (record.getPayType()){
                case 1:
                    vo.setPayType("临时车");
                    break;
                case 2:
                    vo.setPayType("包月车");
                    break;
            }
            list.add(vo);
        }
        pageVO.setList(list);
        pageVO.setTotalCount(selectPage.getTotal());
        pageVO.setCurrPage(selectPage.getCurrent());
        pageVO.setPageSize(selectPage.getSize());
        pageVO.setTotalPage(selectPage.getPages());
        return pageVO;
    }
}
