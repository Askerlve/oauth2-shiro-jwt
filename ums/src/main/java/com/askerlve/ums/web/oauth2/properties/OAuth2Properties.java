package com.askerlve.ums.web.oauth2.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Askerlve
 * @Description: 客户端配置
 * @date 2018/4/28下午3:18
 */
@Component("oAuth2Properties")
@ConfigurationProperties(prefix = "ums.security.oauth2")
public class OAuth2Properties {

    private String jwtSigningKey = "defaltjwtsignkey";

    private OAuth2ClientProperties[] clients = {};

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }
}
