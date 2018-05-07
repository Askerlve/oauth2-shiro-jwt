package com.askerlve.ums.controller;

import com.askerlve.ums.exception.AuthException;
import com.askerlve.ums.web.result.ResultInfo;
import com.askerlve.ums.web.result.ResultUtil;
import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Askerlve
 * @Description: 全局异常处理对象
 * @date 2018/4/19下午5:57
 */
@ControllerAdvice
public class GlobalExceptionController {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionController.class);

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public ResultInfo handleShiroException(ShiroException e) {
        this.logException(e);
        return ResultUtil.createFail(105);
    }

    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthException.class)
    @ResponseBody
    public ResultInfo handleAuthException(AuthException e) {
        this.logException(e);
        return ResultUtil.createFail(e.getCode());
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResultInfo globalException(Throwable ex) {
        this.logException(ex);
        return ResultUtil.createFail(500);
    }

    private void logException(Throwable e) {
        log.error("There is an exception = {}", e.getMessage(), e);
    }
}
