package com.southwind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.southwind.entity.Log;
import com.southwind.form.LogForm;
import com.southwind.mapper.LogMapper;
import com.southwind.service.LogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2023-08-04
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public PageVO logList(LogForm logForm) {
        Page<Log> page = new Page<>(logForm.getPage(), logForm.getLimit());
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(logForm.getUsername()), "username", logForm.getUsername())
                .like(StringUtils.isNotBlank(logForm.getOperation()), "operation", logForm.getOperation())
                .between(StringUtils.isNotBlank(logForm.getStartDate()) && StringUtils.isNotBlank(logForm.getEndDate()), "create_time", logForm.getStartDate(), logForm.getEndDate());
        Page<Log> resultPage = this.logMapper.selectPage(page, queryWrapper);
        PageVO pageVO = new PageVO();
        pageVO.setTotalCount(resultPage.getTotal());
        pageVO.setPageSize(resultPage.getSize());
        pageVO.setCurrPage(resultPage.getCurrent());
        pageVO.setTotalPage(resultPage.getPages());
        pageVO.setList(resultPage.getRecords());
        return pageVO;
    }
}
