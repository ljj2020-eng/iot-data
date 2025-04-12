package com.southwind.service;

import com.southwind.entity.Log;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.form.LogForm;
import com.southwind.vo.PageVO;

/**
 * <p>
 * 操作日志表 服务类
 * </p>
 *
 * @author admin
 * @since 2023-08-04
 */
public interface LogService extends IService<Log> {
    public PageVO logList(LogForm logForm);
}
