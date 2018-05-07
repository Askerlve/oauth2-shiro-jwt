package com.askerlve.ums.dao;

import com.askerlve.ums.model.Resource;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 根据用户及项目key查询当前用户拥有的资源
     *
     * @param userId         用户id
     * @param applicationKey 项目key
     * @return
     */
    List<Resource> getResourcesByUserAndAppKey(@Param("userId") Integer userId, @Param("applicationKey") String applicationKey);

    /**
     * 获取当前项目所有资源
     *
     * @param applicationKey
     * @return
     */
    List<Resource> getResourcesByAppKey(@Param("applicationKey") String applicationKey);
}