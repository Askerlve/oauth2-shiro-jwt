package com.askerlve.ums.service;

import com.askerlve.ums.model.User;
import com.baomidou.mybatisplus.service.IService;

public interface IUserService extends IService<User> {

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    User getUserByUserName(String username);
}
