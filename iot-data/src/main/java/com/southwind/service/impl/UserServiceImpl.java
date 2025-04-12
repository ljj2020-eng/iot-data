package com.southwind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.southwind.entity.Role;
import com.southwind.entity.User;
import com.southwind.entity.UserRole;
import com.southwind.form.UserForm;
import com.southwind.mapper.RoleMapper;
import com.southwind.mapper.UserMapper;
import com.southwind.mapper.UserRoleMapper;
import com.southwind.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.vo.PageVO;
import com.southwind.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author admin
 * @since 2023-07-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageVO userList(UserForm userForm) {
        Page<User> page = new Page<>(userForm.getPage(), userForm.getLimit());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(userForm.getUsername()), "username", userForm.getUsername())
                .like(StringUtils.isNotBlank(userForm.getRealName()), "real_name", userForm.getRealName());
        Page<User> resultPage = this.userMapper.selectPage(page, queryWrapper);
        PageVO pageVO = new PageVO();
        pageVO.setTotalCount(resultPage.getTotal());
        pageVO.setPageSize(resultPage.getSize());
        pageVO.setCurrPage(resultPage.getCurrent());
        pageVO.setTotalPage(resultPage.getPages());
        List<UserVO> list = new ArrayList<>();
        for (User record : resultPage.getRecords()) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(record, userVO);
            QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
            userRoleQueryWrapper.eq("user_id",record.getUserId());
            UserRole userRole = this.userRoleMapper.selectOne(userRoleQueryWrapper);
            QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
            roleQueryWrapper.eq("role_id", userRole.getRoleId());
            Role role = this.roleMapper.selectOne(roleQueryWrapper);
            userVO.setRoleName(role.getRoleName());
            list.add(userVO);
        }
        pageVO.setList(list);
        return pageVO;
    }
}
