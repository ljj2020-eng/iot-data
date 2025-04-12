package com.southwind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.southwind.entity.InOutRecord;
import com.southwind.form.InOutQueryForm;
import com.southwind.mapper.InOutRecordMapper;
import com.southwind.mapper.ParkMapper;
import com.southwind.service.InOutRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.vo.ChartVO;
import com.southwind.vo.InOutRecordVO;
import com.southwind.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2023-07-26
 */
@Service
public class InOutRecordServiceImpl extends ServiceImpl<InOutRecordMapper, InOutRecord> implements InOutRecordService {

    @Autowired
    private InOutRecordMapper inOutRecordMapper;
    @Autowired
    private ParkMapper parkMapper;

    @Override
    public Map chart() {
        List<ChartVO> chartVOList = this.inOutRecordMapper.chart();
        List<String> names = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();
        for (ChartVO chartVO : chartVOList) {
            names.add(chartVO.getName());
            nums.add(chartVO.getValue());
        }
        Map<String,List> map = new HashMap<>();
        map.put("names", names);
        map.put("nums", nums);
        return map;
    }

    @Override
    public PageVO inOutRecordList(InOutQueryForm inOutQueryForm) {
        Page<InOutRecord> page = new Page<>(inOutQueryForm.getPage(),inOutQueryForm.getLimit());
        QueryWrapper<InOutRecord> inOutRecordQueryWrapper = new QueryWrapper<>();
        inOutRecordQueryWrapper.like(StringUtils.isNotBlank(inOutQueryForm.getNumber()), "number", inOutQueryForm.getNumber());
        inOutRecordQueryWrapper.between(StringUtils.isNotBlank(inOutQueryForm.getStartDate()) && StringUtils.isNotBlank(inOutQueryForm.getEndDate()), "in_time", inOutQueryForm.getStartDate(), inOutQueryForm.getEndDate());
        Page<InOutRecord> resultPage = this.inOutRecordMapper.selectPage(page, inOutRecordQueryWrapper);
        PageVO pageVO = new PageVO();
        pageVO.setTotalCount(resultPage.getTotal());
        pageVO.setPageSize(resultPage.getSize());
        pageVO.setCurrPage(resultPage.getCurrent());
        pageVO.setTotalPage(resultPage.getPages());
        List<InOutRecordVO> list = new ArrayList<>();
        for (InOutRecord record : resultPage.getRecords()) {
            InOutRecordVO inOutRecordVO = new InOutRecordVO();
            BeanUtils.copyProperties(record, inOutRecordVO);
            if(record.getPayType() == 1) {
                inOutRecordVO.setPayType("临时车");
            } else {
                inOutRecordVO.setPayType("包月车");
            }
            inOutRecordVO.setParkName(this.parkMapper.selectById(record.getParkId()).getParkName());
            list.add(inOutRecordVO);
        }
        pageVO.setList(list);
        return pageVO;
    }
}
