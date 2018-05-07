package com.askerlve.ums.web.oauth2.handler;

import com.alibaba.fastjson.JSON;
import com.askerlve.ums.exception.AuthException;
import com.askerlve.ums.web.result.ResultInfo;
import com.askerlve.ums.web.result.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Askerlve
 * @Description: 自定义登出成功处理器
 * @date 2018/4/28下午5:33
 */
@Component("appLogoutSuccessHandler")
public class AppLogoutSuccessHandler implements LogoutSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppLogoutSuccessHandler.class);

    private static final String BEARER_AUTHENTICATION = "Bearer ";
    private static final String HEADER_AUTHORIZATION = "Authorization";

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        ResultInfo resultInfo = ResultUtil.createSuccess(200, null);

        try {
            String token = httpServletRequest.getHeader(HEADER_AUTHORIZATION);

            if (token != null && token.startsWith(BEARER_AUTHENTICATION)) {

                OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token.split(" ")[1]);

                if (Objects.nonNull(oAuth2AccessToken)) {
                    tokenStore.removeAccessToken(oAuth2AccessToken);
                    OAuth2RefreshToken oAuth2RefreshToken = oAuth2AccessToken.getRefreshToken();
                    if (Objects.nonNull(oAuth2RefreshToken)) {
                        tokenStore.removeRefreshToken(oAuth2AccessToken.getRefreshToken());
                    }
                } else {
                    throw new AuthException("Token is invalid!", 119);
                }

            } else {
                throw new AuthException("Token is invalid!", 119);
            }
        } catch (Exception e) {
            LOGGER.error("AppLogoutSuccessHandler occur an exception!", e);
            if (e instanceof AuthException) {
                resultInfo = ResultUtil.createFail(((AuthException) e).getCode());
            } else {
                resultInfo = ResultUtil.createFail(500);
                resultInfo.setMessage(e.getMessage());
            }
        }

        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(resultInfo));
    }
}
