package com.askerlve.ums.service.impl;

import com.askerlve.ums.dao.ResourceMapper;
import com.askerlve.ums.model.Resource;
import com.askerlve.ums.model.User;
import com.askerlve.ums.service.IResourceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Resource> getResourcesByUserAndAppKey(User currentUser, String applicationKey) {

        List<Resource> resourceList = null;

        if (Objects.nonNull(currentUser) && Objects.nonNull(currentUser.getId()) && Objects.nonNull(applicationKey)) {
            resourceList = this.resourceMapper.getResourcesByUserAndAppKey(currentUser.getId(), applicationKey);
        }

        return resourceList;

    }

    @Override
    public List<Resource> getResourcesByAppKey(String applicationKey) {

        List<Resource> resourceList = null;

        if (StringUtils.isNotBlank(applicationKey)) {
            resourceList = this.resourceMapper.getResourcesByAppKey(applicationKey);
        }

        return resourceList;
    }

}
