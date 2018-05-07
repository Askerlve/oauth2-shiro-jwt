package com.askerlve.ums.web.oauth2.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Askerlve
 * @Description: 客户端配置
 * @date 2018/4/28下午3:17
 */
@Configuration
@EnableConfigurationProperties(OAuth2ClientProperties.class)
public class OAuth2CoreConfig {
}
