package com.askerlve.ums.service;

import com.askerlve.ums.model.Resource;
import com.askerlve.ums.model.User;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

public interface IResourceService extends IService<Resource> {

    /**
     * 根据用户及项目key查询当前用户拥有的资源
     *
     * @param currentUser    用户
     * @param applicationKey 项目key
     * @return
     */
    List<Resource> getResourcesByUserAndAppKey(User currentUser, String applicationKey);

    /**
     * 获取当前项目所有资源
     *
     * @param applicationKey
     * @return
     */
    List<Resource> getResourcesByAppKey(String applicationKey);
}
