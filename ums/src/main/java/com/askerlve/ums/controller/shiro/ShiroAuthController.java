package com.askerlve.ums.controller.shiro;

import com.askerlve.ums.controller.base.BaseController;
import com.askerlve.ums.controller.shiro.dto.auth.AuthTokenParams;
import com.askerlve.ums.controller.shiro.dto.auth.JudgePerMissionparams;
import com.askerlve.ums.exception.AuthException;
import com.askerlve.ums.model.Application;
import com.askerlve.ums.model.User;
import com.askerlve.ums.service.IApplicationService;
import com.askerlve.ums.service.IResourceService;
import com.askerlve.ums.service.IUserService;
import com.askerlve.ums.utils.JWTUtil;
import com.askerlve.ums.utils.content.Config;
import com.askerlve.ums.web.result.ResultInfo;
import com.askerlve.ums.web.result.ResultUtil;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Askerlve
 * @Description: 鉴权controller
 * @date 2018/4/19下午6:06
 */
@RestController
@Api(tags = "基于Shiro鉴权服务", description = "API接口")
@RequestMapping("/shiro/auth")
public class ShiroAuthController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroAuthController.class);

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IResourceService iResourceService;

    @Autowired
    private IApplicationService iApplicationService;

    @Value("${jwt.token.active.time}")
    private long activeTime = 5 * 60 * 1000;

    @PostMapping(value = "/token")
    @ApiOperation(value = "获取token")
    public ResultInfo getToken(@RequestBody AuthTokenParams authTokenParms) {

        ResultInfo resultInfo = ResultUtil.createSuccess(200, null);

        if (Objects.nonNull(authTokenParms) && StringUtils.isNoneBlank(authTokenParms.getApplicationKey()) &&
                StringUtils.isNoneBlank(authTokenParms.getUserName()) && StringUtils.isNoneBlank(authTokenParms.getPassword())) {

            User user = this.iUserService.getUserByUserName(authTokenParms.getUserName());

            if (Objects.isNull(user)) {
                throw new AuthException("User doesn't existed!", 101);
            }

            Application application = this.iApplicationService.getApplicationByAppkey(authTokenParms.getApplicationKey());
            if (Objects.isNull(application)) {
                throw new AuthException("Application " + authTokenParms.getApplicationKey() + " doesn't exist!", 120);
            }

            if (authTokenParms.getPassword().equals(user.getPassword())) {
                try {
                    Map<String, String> resultMap = new HashMap<>();
                    resultMap.put("token", JWTUtil.sign(user.getUsername(), user.getSecret(), authTokenParms.getApplicationKey(), activeTime));
                    resultInfo.setResult(resultMap);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("Method getToken occur an UnsupportedEncodingException!", e);
                    throw new AuthException("Params invalid!", 504);
                }
            } else {
                throw new AuthException("Password error!", 114);
            }

        } else {
            throw new AuthException("Params invalid!", 504);
        }

        return resultInfo;
    }

    @PostMapping(value = "/judge/permission")
    @ApiOperation(value = "判断当前用户是否有权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Config.JWT_CUSTOMER_TOKEN_NAME, value = "认证token", required = true, dataType = "string", paramType = "header")
    })
    @RequiresAuthentication
    public ResultInfo judgePermission(@RequestBody JudgePerMissionparams judgePerMissionparams) {

        ResultInfo resultInfo = ResultUtil.createSuccess(200, null);

        if (Objects.nonNull(judgePerMissionparams) && StringUtils.isNotBlank(judgePerMissionparams.getUrlAddress())) {

            boolean isAllowed = SecurityUtils.getSubject().isPermitted(judgePerMissionparams.getUrlAddress());
            Map<String, Boolean> stringBooleanMap = new HashMap<>();
            stringBooleanMap.put("isAllowed", isAllowed);
            resultInfo.setResult(stringBooleanMap);

        } else {
            throw new AuthException("Params invalid!", 504);
        }

        return resultInfo;
    }

    @GetMapping(value = "/list/permission")
    @ApiOperation(value = "获取权限列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Config.JWT_CUSTOMER_TOKEN_NAME, value = "认证token", required = true, dataType = "string", paramType = "header")
    })
    @RequiresAuthentication
    public ResultInfo listPermission(@RequestHeader("Authorization") String token) {

        ResultInfo resultInfo = ResultUtil.createSuccess(200, null);

        if (SecurityUtils.getSubject().isAuthenticated()) {
            String applicationKey = JWTUtil.getApplicationKey(token);
            String username = JWTUtil.getUsername(token);
            User user = this.iUserService.getUserByUserName(username);
            if (SecurityUtils.getSubject().hasRole(Config.SUPER_ADMIN_ROLE)) {
                resultInfo.setResult(this.iResourceService.getResourcesByAppKey(applicationKey));
            } else {
                resultInfo.setResult(this.iResourceService.getResourcesByUserAndAppKey(user, applicationKey));
            }
        } else {
            throw new AuthException("Params invalid!", 504);
        }

        return resultInfo;
    }

    @GetMapping(value = "/401")
    @ApiIgnore
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultInfo unauthorized() {
        return ResultUtil.createFail(119);
    }

}
