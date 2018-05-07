package com.askerlve.ums.controller.oauth2;

import com.askerlve.ums.controller.base.BaseController;
import com.askerlve.ums.controller.oauth2.dto.role.Oauth2JudgeRoleParams;
import com.askerlve.ums.controller.oauth2.dto.role.Oauth2JudgeSuperParams;
import com.askerlve.ums.controller.oauth2.dto.role.Oauth2ListRoleParams;
import com.askerlve.ums.exception.AuthException;
import com.askerlve.ums.model.Application;
import com.askerlve.ums.model.Role;
import com.askerlve.ums.model.User;
import com.askerlve.ums.service.IApplicationService;
import com.askerlve.ums.service.IRoleService;
import com.askerlve.ums.service.IUserService;
import com.askerlve.ums.utils.content.Config;
import com.askerlve.ums.web.result.ResultInfo;
import com.askerlve.ums.web.result.ResultUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Askerlve
 * @Description: 角色服务
 * @date 2018/5/3下午5:54
 */
@RestController
@Api(tags = "基于Oauth2角色服务", description = "API接口")
@RequestMapping("/oauth/role")
public class Oauth2RoleController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Oauth2RoleController.class);


    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IApplicationService iApplicationService;

    @PostMapping(value = "/list/role")
    @ApiOperation(value = "获取当前用户拥有的角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Config.JWT_CUSTOMER_TOKEN_NAME, value = "认证token", required = true, dataType = "string", paramType = "header")
    })
    public ResultInfo listRole(@RequestBody Oauth2ListRoleParams oauth2ListRoleParams, Authentication authentication) {

        ResultInfo resultInfo = ResultUtil.createSuccess(200, null);

        if (Objects.nonNull(oauth2ListRoleParams) && StringUtils.isNotBlank(oauth2ListRoleParams.getApplicationKey())) {

            String userName = authentication.getName();
            User user = this.iUserService.getUserByUserName(userName);
            if (user == null) {
                throw new AuthException("User doesn't exist!", 101);
            }

            String applicationKey = oauth2ListRoleParams.getApplicationKey();
            Application application = this.iApplicationService.getApplicationByAppkey(applicationKey);
            if (Objects.isNull(application)) {
                throw new AuthException("Application " + applicationKey + " doesn't exist!", 120);
            }

            List<Role> roles = this.iRoleService.getRolesByUserAndAppKey(user, applicationKey);

            resultInfo.setResult(roles);

        } else {
            throw new AuthException("Params invalid!", 504);
        }

        return resultInfo;
    }

    @PostMapping(value = "/judge/super")
    @ApiOperation(value = "判断当前用户是否拥有超级管理员角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Config.JWT_CUSTOMER_TOKEN_NAME, value = "认证token", required = true, dataType = "string", paramType = "header")
    })
    public ResultInfo judgeSuper(@RequestBody Oauth2JudgeSuperParams oauth2JudgeSuperParams, Authentication authentication) {

        ResultInfo resultInfo = ResultUtil.createSuccess(200, null);

        if (Objects.nonNull(oauth2JudgeSuperParams) && StringUtils.isNotBlank(oauth2JudgeSuperParams.getApplicationKey())) {

            String userName = authentication.getName();
            User user = this.iUserService.getUserByUserName(userName);
            if (user == null) {
                throw new AuthException("User doesn't exist!", 101);
            }

            String applicationKey = oauth2JudgeSuperParams.getApplicationKey();
            Application application = this.iApplicationService.getApplicationByAppkey(applicationKey);
            if (Objects.isNull(application)) {
                throw new AuthException("Application " + applicationKey + " doesn't exist!", 120);
            }

            List<Role> roles = this.iRoleService.getRolesByUserAndAppKey(user, applicationKey);
            Map<String, Boolean> booleanMap = Maps.newHashMap();
            booleanMap.put("isSuperAdmin", this.iRoleService.hasRole(roles, Config.SUPER_ADMIN_ROLE));
            resultInfo.setResult(booleanMap);

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
    public ResultInfo judgeRole(@RequestBody Oauth2JudgeRoleParams oauth2JudgeRoleParams, Authentication authentication) {

        ResultInfo resultInfo = ResultUtil.createSuccess(200, null);

        if (Objects.nonNull(oauth2JudgeRoleParams) && StringUtils.isNotBlank(oauth2JudgeRoleParams.getApplicationKey()) && StringUtils.isNotBlank(oauth2JudgeRoleParams.getRoleKey())) {

            String userName = authentication.getName();
            User user = this.iUserService.getUserByUserName(userName);
            if (user == null) {
                throw new AuthException("User doesn't exist!", 101);
            }

            String applicationKey = oauth2JudgeRoleParams.getApplicationKey();
            Application application = this.iApplicationService.getApplicationByAppkey(applicationKey);
            if (Objects.isNull(application)) {
                throw new AuthException("Application " + applicationKey + " doesn't exist!", 120);
            }

            String roleKey = oauth2JudgeRoleParams.getRoleKey();
            List<Role> roles = this.iRoleService.getRolesByUserAndAppKey(user, applicationKey);
            Map<String, Object> retMap = Maps.newHashMap();
            retMap.put("judgeRoleKey", roleKey);
            retMap.put("isOwn", this.iRoleService.hasRole(roles, roleKey));
            resultInfo.setResult(retMap);

        } else {
            throw new AuthException("Params invalid!", 504);
        }

        return resultInfo;
    }
}
