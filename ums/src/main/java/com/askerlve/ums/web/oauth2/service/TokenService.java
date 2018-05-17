package com.askerlve.ums.web.oauth2.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Askerlve
 * @Description: oauth2 token生成服务
 * @date 2018/5/15下午5:55
 */
@Component("tokenService")
public class TokenService {

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    /**
     * creatAccessToken
     * @param Authorization basic Authorization token
     * @param authentication 认证通过后的authentication
     * @return
     * @throws IOException
     */
    public OAuth2AccessToken creatAccessToken(String Authorization, Authentication authentication) throws IOException {

        assert authentication != null;

        return this.getOAuth2AccessToken(GrantType.PASSWORD, Authorization, authentication, null);
    }

    /**
     * refreshAccessToken
     * @param Authorization basic Authorization token
     * @param refresh_token 刷新token
     * @return
     * @throws IOException
     */
    public OAuth2AccessToken refreshAccessToken(String Authorization, String refresh_token) throws IOException {

        assert StringUtils.isNotBlank(refresh_token);

        return this.getOAuth2AccessToken(GrantType.REFRESH_TOKRN, Authorization, null, refresh_token);

    }

    private OAuth2AccessToken getOAuth2AccessToken(GrantType grantType, String Authorization, Authentication authentication, String refresh_token) throws IOException {
        if (Authorization == null || !Authorization.startsWith("Basic ")) {
            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
        }
        String[] tokens = this.extractAndDecodeHeader(Authorization);

        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];

        ClientDetails clientDetails = this.clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId 对应的配置信息不存在" + clientId);
        } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
            throw new UnapprovedClientAuthenticationException("clientSecret 不匹配" + clientId);
        }

        TokenRequest tokenRequest = new TokenRequest(new HashMap<>(), clientId, clientDetails.getScope(), "password");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2AccessToken token = null;

        switch (grantType) {
            case PASSWORD:
                OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
                token = this.authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
                break;
            case REFRESH_TOKRN:
                token = this.authorizationServerTokenServices.refreshAccessToken(refresh_token, tokenRequest);
        }

        return token;

    }


    private enum GrantType {
        REFRESH_TOKRN, PASSWORD;
    }

    /**
     * 解码
     *
     * @param header
     * @return
     * @throws IOException
     */
    private String[] extractAndDecodeHeader(String header) throws IOException {
        byte[] base64Token = header.substring(6).getBytes("UTF-8");

        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException var7) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        } else {
            return new String[]{token.substring(0, delim), token.substring(delim + 1)};
        }
    }

}
