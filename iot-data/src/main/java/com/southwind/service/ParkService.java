package com.southwind.service;

import com.southwind.entity.Park;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.form.ParkListForm;
import com.southwind.vo.PageVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2023-12-12
 */
public interface ParkService extends IService<Park> {
    public PageVO parkList(ParkListForm parkListForm);
}
