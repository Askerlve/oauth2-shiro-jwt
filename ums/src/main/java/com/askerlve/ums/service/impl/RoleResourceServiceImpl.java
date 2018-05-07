package com.askerlve.ums.service.impl;

import com.askerlve.ums.dao.RoleResourceMapper;
import com.askerlve.ums.model.RoleResource;
import com.askerlve.ums.service.IRoleResourceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements IRoleResourceService {

}
