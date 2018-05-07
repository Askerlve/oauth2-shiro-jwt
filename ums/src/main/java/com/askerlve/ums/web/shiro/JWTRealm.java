package com.askerlve.ums.web.shiro;

import com.askerlve.ums.model.Application;
import com.askerlve.ums.model.Resource;
import com.askerlve.ums.model.Role;
import com.askerlve.ums.model.User;
import com.askerlve.ums.service.IApplicationService;
import com.askerlve.ums.service.IResourceService;
import com.askerlve.ums.service.IRoleService;
import com.askerlve.ums.service.IUserService;
import com.askerlve.ums.utils.JWTUtil;
import com.askerlve.ums.utils.content.Config;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Askerlve
 * @Description: 自定义shiro realm
 * @date 2018/4/19下午4:07
 */
@Component
public class JWTRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTRealm.class);

    @Autowired
    @Qualifier("iUserService")
    private IUserService iUserService;

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IResourceService iResourceService;

    @Autowired
    private IApplicationService iApplicationService;


    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String jwtToken = principals.toString();
        String username = JWTUtil.getUsername(jwtToken);
        String applicationKey = JWTUtil.getApplicationKey(jwtToken);

        User user = this.iUserService.getUserByUserName(username);
        List<Role> roles = this.iRoleService.getRolesByUserAndAppKey(user, applicationKey);

        List<Resource> resources;

        if (this.iRoleService.hasRole(roles, Config.SUPER_ADMIN_ROLE)) {

            /**
             * 若为超级管理员，加载该项目所有权限
             */
            resources = this.iResourceService.getResourcesByAppKey(applicationKey);

        } else {

            resources = this.iResourceService.getResourcesByUserAndAppKey(user, applicationKey);

        }

        return this.buildSimpleAuthorizationInfo(roles, resources);
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String jwtToken = (String) auth.getCredentials();

        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(jwtToken);
        String applicationKey = JWTUtil.getApplicationKey(jwtToken);
        if (StringUtils.isBlank(username) || StringUtils.isBlank(applicationKey)) {
            throw new AuthenticationException("Token is invalid!");
        }

        User user = this.iUserService.getUserByUserName(username);
        if (user == null) {
            throw new AuthenticationException("User doesn't exist!");
        }

        Application application = this.iApplicationService.getApplicationByAppkey(applicationKey);
        if (Objects.isNull(application)) {
            throw new AuthenticationException("Application " + applicationKey + " doesn't exist!");
        }

        try {
            JWTUtil.verify(jwtToken, username, user.getSecret());
        } catch (Exception e) {
            LOGGER.error("Method doGetAuthenticationInfo occur an unknown error!", e);
            throw new AuthenticationException("Token is invalid!");
        }

        return new SimpleAuthenticationInfo(jwtToken, jwtToken, "jwtRealm");
    }

    private SimpleAuthorizationInfo buildSimpleAuthorizationInfo(List<Role> roles, List<Resource> resources) {

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        if (Objects.nonNull(roles) && roles.size() > 0) {
            Set<String> roleSets = new LinkedHashSet<>();
            for (Role role : roles) {
                roleSets.add(role.getRoleKey());
            }
            simpleAuthorizationInfo.addRoles(roleSets);
        }

        if (Objects.nonNull(resources) && resources.size() > 0) {
            Set<String> permissionSets = new LinkedHashSet<>();
            for (Resource resource : resources) {
                permissionSets.add(resource.getUrl());
            }
            simpleAuthorizationInfo.addStringPermissions(permissionSets);
        }

        return simpleAuthorizationInfo;
    }

}
