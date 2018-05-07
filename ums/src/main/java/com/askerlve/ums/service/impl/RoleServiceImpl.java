package com.askerlve.ums.service.impl;

import com.askerlve.ums.dao.RoleMapper;
import com.askerlve.ums.model.Role;
import com.askerlve.ums.model.User;
import com.askerlve.ums.service.IRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Role> getRolesByUserAndAppKey(User currentUser, String applicationKey) {

        List<Role> roleList = Lists.newArrayList();

        if (Objects.nonNull(currentUser) && Objects.nonNull(currentUser.getId())) {
            roleList = roleMapper.getRolesByUserAndAppKey(currentUser.getId(), applicationKey);
        }

        return roleList;
    }

    /**
     * 判断是否超级管理员,条件:是否拥有roleKey为admin的角色
     *
     * @param roles
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public boolean hasRole(List<Role> roles, String roleKey) {

        if (StringUtils.isNotBlank(roleKey) && Objects.nonNull(roles) && roles.size() > 0) {

            for (Role role : roles) {
                if (roleKey.equalsIgnoreCase(role.getRoleKey())) {
                    return true;
                }
            }

        }

        return false;
    }
}
