package com.southwind.service;

import com.southwind.entity.PayRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.form.PayListForm;
import com.southwind.vo.PageVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2023-12-13
 */
public interface PayRecordService extends IService<PayRecord> {
    public PageVO payList(PayListForm payListForm);
}
