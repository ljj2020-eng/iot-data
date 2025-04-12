package com.southwind.service;

import com.southwind.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.form.UserForm;
import com.southwind.vo.PageVO;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author admin
 * @since 2023-07-24
 */
public interface UserService extends IService<User> {
    public PageVO userList(UserForm userForm);
}
