package com.southwind.service;

import com.southwind.entity.InOutRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.form.InOutQueryForm;
import com.southwind.vo.PageVO;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2023-07-26
 */
public interface InOutRecordService extends IService<InOutRecord> {
    public Map chart();
    public PageVO inOutRecordList(InOutQueryForm inOutQueryForm);
}
