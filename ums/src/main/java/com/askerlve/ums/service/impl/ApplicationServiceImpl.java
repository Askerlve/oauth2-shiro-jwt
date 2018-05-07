package com.askerlve.ums.service.impl;

import com.askerlve.ums.dao.ApplicationMapper;
import com.askerlve.ums.exception.AuthException;
import com.askerlve.ums.model.Application;
import com.askerlve.ums.service.IApplicationService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements IApplicationService {

    @Override
    @Transactional(readOnly = true)
    public Application getApplicationByAppkey(String applicationKey) {
        if (StringUtils.isBlank(applicationKey)) {
            throw new AuthException("Params invalid!", 504);
        }
        EntityWrapper entityWrapper = new EntityWrapper<Application>();
        entityWrapper.eq("application_key", applicationKey);
        return this.selectOne(entityWrapper);
    }
}
