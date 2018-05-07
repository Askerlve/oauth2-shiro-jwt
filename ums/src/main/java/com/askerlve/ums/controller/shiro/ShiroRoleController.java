package com.askerlve.ums.controller.shiro;

import com.askerlve.ums.controller.base.BaseController;
import com.askerlve.ums.controller.shiro.dto.role.JudgeRoleParams;
import com.askerlve.ums.exception.AuthException;
import com.askerlve.ums.model.Role;
import com.askerlve.ums.model.User;
import com.askerlve.ums.service.IRoleService;
import com.askerlve.ums.service.IUserService;
import com.askerlve.ums.utils.JWTUtil;
import com.askerlve.ums.utils.content.Config;
import com.askerlve.ums.web.result.ResultInfo;
import com.askerlve.ums.web.result.ResultUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Askerlve
 * @Description: 角色服务
 * @date 2018/4/25上午10:23
 */
@RestController
@Api(tags = "基于Shiro角色服务", description = "API接口")
@RequestMapping("/shiro/role")
public class ShiroRoleController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroRoleController.class);

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IUserService iUserService;

    @GetMapping(value = "/list/role")
    @ApiOperation(value = "获取当前用户拥有的角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Config.JWT_CUSTOMER_TOKEN_NAME, value = "认证token", required = true, dataType = "string", paramType = "header")
    })
    @RequiresAuthentication
    public ResultInfo listRole(@RequestHeader(value = Config.JWT_CUSTOMER_TOKEN_NAME) String token) {

        ResultInfo resultInfo = ResultUtil.createSuccess(200, null);

        if (StringUtils.isNotBlank(token)) {

            boolean isAllowed = SecurityUtils.getSubject().isAuthenticated();

            if (isAllowed) {

                String userName = JWTUtil.getUsername(token);
                String applicationKey = JWTUtil.getApplicationKey(token);
                User user = this.iUserService.getUserByUserName(userName);
                List<Role> roles = this.iRoleService.getRolesByUserAndAppKey(user, applicationKey);
                resultInfo.setResult(roles);

            } else {
                throw new AuthException("Token invalid!", 119);
            }

        } else {
            throw new AuthException("Params invalid!", 504);
        }

        return resultInfo;
    }

    @GetMapping(value = "/judge/super")
    @ApiOperation(value = "判断当前用户是否拥有超级管理员角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Config.JWT_CUSTOMER_TOKEN_NAME, value = "认证token", required = true, dataType = "string", paramType = "header")
    })
    @RequiresAuthentication
    public ResultInfo judgeSuper(@RequestHeader(value = Config.JWT_CUSTOMER_TOKEN_NAME) String token) {

        ResultInfo resultInfo = ResultUtil.createSuccess(200, null);

        if (StringUtils.isNotBlank(token)) {

            boolean isAllowed = SecurityUtils.getSubject().isAuthenticated();

            if (isAllowed) {

                String userName = JWTUtil.getUsername(token);
                String applicationKey = JWTUtil.getApplicationKey(token);
                User user = this.iUserService.getUserByUserName(userName);
                List<Role> roles = this.iRoleService.getRolesByUserAndAppKey(user, applicationKey);
                Map<String, Boolean> booleanMap = Maps.newHashMap();
                booleanMap.put("isSuperAdmin", this.iRoleService.hasRole(roles, Config.SUPER_ADMIN_ROLE));
                resultInfo.setResult(booleanMap);

            } else {
                throw new AuthException("Token invalid!", 119);
            }

        } else {
            throw new AuthException("Params invalid!", 504);
        }

        return resultInfo;
    }

    @PostMapping(value = "/judge/role")
    @ApiOperation(value = "判断当前用户是否拥有某个角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Config.JWT_CUSTOMER_TOKEN_NAME, value = "认证token", required = true, dataType = "string", paramType = "header")
    })
    @RequiresAuthentication
    public ResultInfo judgeRole(@RequestHeader(value = Config.JWT_CUSTOMER_TOKEN_NAME) String token, @RequestBody JudgeRoleParams judgeRoleParams) {

        ResultInfo resultInfo = ResultUtil.createSuccess(200, null);

        if (StringUtils.isNotBlank(token) && Objects.nonNull(judgeRoleParams) && StringUtils.isNotBlank(judgeRoleParams.getRoleKey())) {

            boolean isAllowed = SecurityUtils.getSubject().isAuthenticated();
            String roleKey = judgeRoleParams.getRoleKey();

            if (isAllowed) {

                String userName = JWTUtil.getUsername(token);
                String applicationKey = JWTUtil.getApplicationKey(token);
                User user = this.iUserService.getUserByUserName(userName);
                List<Role> roles = this.iRoleService.getRolesByUserAndAppKey(user, applicationKey);
                Map<String, Object> retMap = Maps.newHashMap();
                retMap.put("judgeRoleKey",roleKey);
                retMap.put("isOwn", this.iRoleService.hasRole(roles, roleKey));
                resultInfo.setResult(retMap);

            } else {
                throw new AuthException("Token invalid!", 119);
            }

        } else {
            throw new AuthException("Params invalid!", 504);
        }

        return resultInfo;
    }

}
