package com.askerlve.ums.dao;

import com.askerlve.ums.model.Role;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询当前用户锁拥有当前系统的角色集合
     *
     * @param userId
     * @param applicationKey
     * @return
     */
    List<Role> getRolesByUserAndAppKey(@Param("userId") Integer userId, @Param("applicationKey") String applicationKey);
}