package com.southwind.service;

import com.southwind.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.form.RoleForm;
import com.southwind.vo.PageVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2023-07-25
 */
public interface RoleService extends IService<Role> {
    public PageVO roleList(RoleForm roleForm);
}
