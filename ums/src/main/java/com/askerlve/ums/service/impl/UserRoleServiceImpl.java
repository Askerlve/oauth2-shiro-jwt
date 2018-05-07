package com.askerlve.ums.service.impl;

import com.askerlve.ums.dao.UserRoleMapper;
import com.askerlve.ums.model.UserRole;
import com.askerlve.ums.service.IUserRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
