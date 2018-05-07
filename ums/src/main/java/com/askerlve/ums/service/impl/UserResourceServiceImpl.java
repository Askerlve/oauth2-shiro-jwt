package com.askerlve.ums.service.impl;

import com.askerlve.ums.dao.UserResourceMapper;
import com.askerlve.ums.model.UserResource;
import com.askerlve.ums.service.IUserResourceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserResourceServiceImpl extends ServiceImpl<UserResourceMapper, UserResource> implements IUserResourceService {

}

