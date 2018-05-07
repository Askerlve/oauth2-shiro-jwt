package com.askerlve.ums.controller.oauth2.dto.oauth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Askerlve
 * @Description: Oauth2鉴权入参实体
 * @date 2018/5/3下午5:26
 */
@ApiModel("Oauth2鉴权入参实体")
public class Oauth2JudgePermissionParams {

    @ApiModelProperty(value = "需要鉴权的地址", required = true)
    private String urlAddress;

    @ApiModelProperty(value = "需要鉴权的项目key", required = true)
    private String applicationKey;

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public String getApplicationKey() {
        return applicationKey;
    }

    public void setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
    }
}
