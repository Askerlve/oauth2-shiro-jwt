package com.askerlve.ums.service;

import com.askerlve.ums.model.Application;
import com.baomidou.mybatisplus.service.IService;

public interface IApplicationService extends IService<Application> {

    /**
     * 根据appkey获取项目
     * @param applicationKey
     * @return
     */
    Application getApplicationByAppkey(String applicationKey);
}
