package com.askerlve.ums.web.oauth2.handler;

import com.askerlve.ums.web.oauth2.exception.ExceptionReturnBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Askerlve
 * @Description: 自定义登录失败handler
 * @date 2018/5/2下午2:37
 */
@Component("appLoginFailureHandler")
public class AppLoginFailureHandler implements AuthenticationFailureHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppLoginFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        LOGGER.error("AppLoginFailureHandler got an error!",e);
        ExceptionReturnBuilder.buildExceptionReturn(httpServletRequest,httpServletResponse,e);

    }

}
