package com.askerlve.ums.controller.shiro;

import com.askerlve.ums.controller.base.BaseController;
import com.askerlve.ums.exception.AuthException;
import com.askerlve.ums.model.User;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Askerlve
 * @Description: 用户管理api
 * @date 2018/4/24上午11:46
 */
@RestController
@Api(tags = "基于Shiro用户服务", description = "API接口")
@RequestMapping("/shiro/user")
public class ShiroUserController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroUserController.class);

    @Autowired
    private IUserService iUserService;

    @GetMapping(value = "/current")
    @ApiOperation(value = "获取当前用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Config.JWT_CUSTOMER_TOKEN_NAME, value = "认证token", required = true, dataType = "string", paramType = "header")
    })
    @RequiresAuthentication
    public ResultInfo getCurrentUser(@RequestHeader(value = Config.JWT_CUSTOMER_TOKEN_NAME) String token) {

        ResultInfo resultInfo = ResultUtil.createSuccess(200, null);

        if (StringUtils.isNotBlank(token)) {

            boolean isAllowed = SecurityUtils.getSubject().isAuthenticated();

            if (isAllowed) {

                String userName = JWTUtil.getUsername(token);
                User user = this.iUserService.getUserByUserName(userName);
                resultInfo.setResult(user);

            } else {
                throw new AuthException("Token invalid!", 119);
            }

        } else {
            throw new AuthException("Params invalid!", 504);
        }

        return resultInfo;
    }

}
