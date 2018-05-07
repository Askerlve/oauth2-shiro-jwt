package com.askerlve.ums.service;

import com.askerlve.ums.model.Role;
import com.askerlve.ums.model.User;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

public interface IRoleService extends IService<Role> {

    /**
     * 查询当前用户锁拥有当前系统的角色集合
     *
     * @param currentUser
     * @param applicationKey
     * @return
     */
    List<Role> getRolesByUserAndAppKey(User currentUser, String applicationKey);

    /**
     * 判断是否拥有某一个角色
     *
     * @param roles
     * @param roleKey
     * @return
     */
    boolean hasRole(List<Role> roles, String roleKey);
}
