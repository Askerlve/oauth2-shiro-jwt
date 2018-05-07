package com.askerlve.ums.controller.oauth2;

import com.askerlve.ums.controller.base.BaseController;
import com.askerlve.ums.exception.AuthException;
import com.askerlve.ums.model.User;
import com.askerlve.ums.service.IUserService;
import com.askerlve.ums.utils.content.Config;
import com.askerlve.ums.web.result.ResultInfo;
import com.askerlve.ums.web.result.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Askerlve
 * @Description: 用户管理api
 * @date 2018/5/3下午5:50
 */
@RestController
@Api(tags = "基于Oauth2用户服务", description = "API接口")
@RequestMapping("/oauth/user")
public class Oauth2UserController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Oauth2UserController.class);

    @Autowired
    private IUserService iUserService;

    @GetMapping(value = "/current")
    @ApiOperation(value = "获取当前用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Config.JWT_CUSTOMER_TOKEN_NAME, value = "认证token", required = true, dataType = "string", paramType = "header")
    })
    public ResultInfo getCurrentUser(Authentication authentication) {

        ResultInfo resultInfo = ResultUtil.createSuccess(200, null);

        String userName = authentication.getName();
        User user = this.iUserService.getUserByUserName(userName);
        if (user == null) {
            throw new AuthException("User doesn't exist!", 101);
        }

        resultInfo.setResult(user);

        return resultInfo;
    }
}
