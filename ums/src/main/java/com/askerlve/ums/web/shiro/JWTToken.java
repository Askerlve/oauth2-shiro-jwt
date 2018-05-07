package com.askerlve.ums.web.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Askerlve
 * @Description: jwt token
 * @date 2018/4/19下午3:48
 */
public class JWTToken implements AuthenticationToken {

    // 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}
