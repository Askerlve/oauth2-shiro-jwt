package com.askerlve.ums.controller.oauth2.dto.oauth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Askerlve
 * @Description: Oauth2获取权限列表
 * @date 2018/5/3下午5:44
 */
@ApiModel("Oauth2获取权限列表入参实体")
public class Oauth2ListPermissionParams {

    @ApiModelProperty(value = "需要鉴权的项目key", required = true)
    private String applicationKey;

    public String getApplicationKey() {
        return applicationKey;
    }

    public void setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
    }

}
