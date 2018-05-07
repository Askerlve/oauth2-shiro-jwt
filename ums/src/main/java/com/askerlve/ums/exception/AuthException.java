package com.askerlve.ums.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Askerlve
 * @Description: 自定义异常
 * @date 2018/4/19下午5:53
 */
public class AuthException extends AuthenticationException {

    private int code;

    public AuthException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthException(String msg) {
        super(msg);
    }

    public AuthException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public AuthException(String msg, Throwable t, int code) {
        super(msg, t);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
