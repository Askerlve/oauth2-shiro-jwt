package com.askerlve.ums.web.oauth2.handler;

import com.askerlve.ums.web.oauth2.exception.ExceptionReturnBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.AbstractOAuth2SecurityExceptionHandler;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Askerlve
 * @Description: 自定义AccessDeniedHandler
 * @date 2018/5/4上午11:17
 */
public class AppOAuth2AccessDeniedHandler extends AbstractOAuth2SecurityExceptionHandler implements AccessDeniedHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppOAuth2AccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        LOGGER.error("AppOAuth2AccessDeniedHandler got an error!", accessDeniedException);
        ExceptionReturnBuilder.buildExceptionReturn(request, response, accessDeniedException);
    }

}
