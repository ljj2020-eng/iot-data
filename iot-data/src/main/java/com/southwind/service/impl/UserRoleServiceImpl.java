package com.southwind.service.impl;

import com.southwind.entity.UserRole;
import com.southwind.mapper.UserRoleMapper;
import com.southwind.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户与角色对应关系 服务实现类
 * </p>
 *
 * @author admin
 * @since 2023-08-03
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
