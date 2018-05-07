package com.askerlve.ums.service.impl;

import com.askerlve.ums.dao.UserMapper;
import com.askerlve.ums.exception.AuthException;
import com.askerlve.ums.model.User;
import com.askerlve.ums.service.IUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service("iUserService")
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService,UserDetailsService {

    @Override
    @Transactional(readOnly = true)
    public User getUserByUserName(String username) {
        if (StringUtils.isBlank(username)) {
            throw new AuthException("Params invalid!", 504);
        }
        EntityWrapper entityWrapper = new EntityWrapper<User>();
        entityWrapper.eq("username", username);
        entityWrapper.eq("status", 1);
        return this.selectOne(entityWrapper);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = this.getUserByUserName(userName);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User doesn't existed!");
        }
        return user;
    }
}
